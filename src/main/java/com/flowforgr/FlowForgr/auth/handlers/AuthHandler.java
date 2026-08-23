package com.flowforgr.FlowForgr.auth.handlers;

import com.flowforgr.FlowForgr.auth.payload.request.FlowForgrRegisterOrganizationRequest;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthHandler {
    ResponseEntity<FlowForgrApiResponse<?>> handleRegisterAppUserOrganization(FlowForgrRegisterOrganizationRequest flowForgrRegisterOrganizationRequest);
}
