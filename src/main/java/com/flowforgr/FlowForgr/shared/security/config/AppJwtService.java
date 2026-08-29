package com.flowforgr.FlowForgr.shared.security.config;


import com.flowforgr.FlowForgr.auth.entity.AppUser;
import com.flowforgr.FlowForgr.auth.entity.AuthIdentity;
import com.flowforgr.FlowForgr.auth.entity.Role;
import com.flowforgr.FlowForgr.auth.enums.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class AppJwtService {

    @Value("${JWT-SECRET-KEY}")
    private String secretKey;

    public String generateToken(AppUser appUser) {
        Map<String, Object> claims = new HashMap<>(){{
            put("id", appUser.getId());
            put("roles", appUser.getRoles().stream().map(Role::getRoleName).toList());
            put("firstName", appUser.getFirstName());
            put("lastName", appUser.getLastName());
            put("email", appUser.getEmail());
            put("emailVerified", appUser.isEmailVerified());
            put("userType", appUser.getUserType().name());
            put("organizationId", appUser.getOrganization().getId());
        }};

        return Jwts.builder().claims().add(claims)
                .subject(appUser.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (2 * 60 * 60 * 1000)))
                .and().signWith(getSecretKey())
                .compact();
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        Claims allClaims = extractAllClaim(token);
        return claimResolver.apply(allClaims);
    }

    public Claims extractAllClaim(String token) {
        return Jwts.parser().verifyWith(getSecretKey())
                .build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSecretKey() {
        byte[] encodedKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(encodedKey);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date date =  extractClaims(token, Claims::getExpiration);
        return date.before(new Date());
    }

    public AuthIdentity configureAuthIdentity (Claims claims) {
        return AuthIdentity.builder()
                .id(claims.get("id", Long.class))
                .email(claims.getSubject())
                .firstName(claims.get("firstName", String.class))
                .lastName(claims.get("lastName", String.class))
                .roles(claims.get("roles", List.class))
                .emailVerified(claims.get("emailVerified", Boolean.class))
                .userType(claims.get("userType", String.class))
                .organizationId(claims.get("organizationId", Long.class))
                .build();
    }
}
