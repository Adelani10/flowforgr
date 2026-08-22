package com.flowforgr.FlowForgr.shared.security.config;

import com.flowforgr.FlowForgr.auth.entity.AuthIdentity;
import com.flowforgr.FlowForgr.shared.enums.FlowForgrApiRequestType;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppJwtFilter extends OncePerRequestFilter {

    private final AppJwtService appJwtService;
    private final AppUserDetailsService appUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var authorizationHeader = request.getHeader("Authorization");
        String token = "";
        String username = "";

        if (!ObjectUtils.isEmpty(authorizationHeader)) {
            token = authorizationHeader.replace("Bearer ", "");
            username = appJwtService.extractUserName(token);
        }

        try{
            Claims claims = appJwtService.extractAllClaim(token);

            if(!ObjectUtils.isEmpty(username) && ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())) {
                UserDetails userDetails = appUserDetailsService.loadUserByUsername(username);
                if(appJwtService.validateToken(token, userDetails)) {

                    boolean emailVerified = claims.get("emailVerified", Boolean.class);
                    System.out.println("url::" + request.getRequestURI());
                    if (!emailVerified && !request.getRequestURI().equals("/verify-email")) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        sendErrorResponse(response, "Please verify your email");
                        return;
                    }

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            username, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    AuthIdentity authIdentity = appJwtService.configureAuthIdentity(claims);
                    request.setAttribute("AUTH_IDENTITY", authIdentity);
                }
            }
        } catch (ExpiredJwtException e) {
                System.err.println("Error thrown in ExpiredJwtException block");
                sendErrorResponse(response, "Your session has expired. Please log in again.");
                return;
            } catch (JwtException e) {
                System.err.println("Error thrown in JwtException block");
                sendErrorResponse(response, "Invalid token. Please log in again.");
                return;
            } catch (Exception e) {
//                e.printStackTrace();
                System.err.println("Error thrown in Exception block");
                sendErrorResponse(response, "Authentication failed. Please try again.");
                return;
        }
        filterChain.doFilter(request, response);
    }


    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        FlowForgrApiResponse<?> apiResponse = FlowForgrApiResponse.builder()
                .referenceId(UUID.randomUUID().toString())
                .requestTime(LocalDateTime.now())
                .requestType(FlowForgrApiRequestType.OutBound.name())
                .message(message)
                .status(false)
                .error("JWT Error")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(apiResponse));
    }
}
