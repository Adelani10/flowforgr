package com.flowforgr.FlowForgr.auth.handlers;

import com.flowforgr.FlowForgr.auth.payload.request.auth.FlowForgrLoginRequest;
import com.flowforgr.FlowForgr.auth.payload.request.auth.FlowForgrRegisterOrganizationRequest;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthHandler {
    ResponseEntity<FlowForgrApiResponse<?>> handleRegisterAppUserOrganization(FlowForgrRegisterOrganizationRequest flowForgrRegisterOrganizationRequest);
    ResponseEntity<FlowForgrApiResponse<?>> handleFLowForgrLoginAppUser(FlowForgrLoginRequest request);
}
