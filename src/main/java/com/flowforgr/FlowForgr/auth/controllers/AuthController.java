package com.flowforgr.FlowForgr.auth.controllers;


import com.flowforgr.FlowForgr.auth.entity.AuthIdentity;
import com.flowforgr.FlowForgr.auth.handlers.AuthHandler;
import com.flowforgr.FlowForgr.auth.payload.request.FlowForgrLoginRequest;
import com.flowforgr.FlowForgr.auth.payload.request.FlowForgrRegisterOrganizationRequest;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthHandler authHandler;

    @PostMapping("/register")
    public ResponseEntity<FlowForgrApiResponse<?>> registerAppUserOrganization
            (@Valid @RequestBody FlowForgrRegisterOrganizationRequest request) {
        return authHandler.handleRegisterAppUserOrganization(request);
    }

    @GetMapping("/test")
    public ResponseEntity<FlowForgrApiResponse<?>> test(@RequestAttribute ("AUTH_IDENTITY")AuthIdentity  authIdentity) {
        return authHandler.handleTest(authIdentity);
    }

    @PostMapping("/login")
    public ResponseEntity<FlowForgrApiResponse<?>> loginAppUserOrganization(@Valid @RequestBody FlowForgrLoginRequest request) {
        return authHandler.handleFLowForgrLoginAppUser(request);
    }

}
