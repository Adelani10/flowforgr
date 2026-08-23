package com.flowforgr.FlowForgr.auth.controllers;


import com.flowforgr.FlowForgr.auth.handlers.AuthHandler;
import com.flowforgr.FlowForgr.auth.payload.request.FlowForgrRegisterOrganizationRequest;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
