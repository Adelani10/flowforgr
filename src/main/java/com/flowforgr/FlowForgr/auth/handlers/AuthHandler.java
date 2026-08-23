package com.flowforgr.FlowForgr.auth.handlers;

import com.flowforgr.FlowForgr.auth.entity.AuthIdentity;
import com.flowforgr.FlowForgr.auth.payload.request.FlowForgrLoginRequest;
import com.flowforgr.FlowForgr.auth.payload.request.FlowForgrRegisterOrganizationRequest;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthHandler {
    ResponseEntity<FlowForgrApiResponse<?>> handleRegisterAppUserOrganization(FlowForgrRegisterOrganizationRequest flowForgrRegisterOrganizationRequest);
    ResponseEntity<FlowForgrApiResponse<?>> handleTest(AuthIdentity authIdentity);
    ResponseEntity<FlowForgrApiResponse<?>> handleFLowForgrLoginAppUser(FlowForgrLoginRequest request);
}
