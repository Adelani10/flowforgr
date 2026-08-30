package com.flowforgr.FlowForgr.auth.handlers;

import com.flowforgr.FlowForgr.shared.entity.AuthIdentity;
import com.flowforgr.FlowForgr.auth.payload.request.role.CreateRoleRequest;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import org.springframework.http.ResponseEntity;

public interface RoleHandler {

    ResponseEntity<FlowForgrApiResponse<?>> handleCreateRole(CreateRoleRequest request, AuthIdentity authIdentity);
    ResponseEntity<FlowForgrApiResponse<?>> handleUpdateRole(CreateRoleRequest request, AuthIdentity authIdentity);
    ResponseEntity<FlowForgrApiResponse<?>> deleteRole(Long id, AuthIdentity authIdentity);
}
