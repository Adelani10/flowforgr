package com.flowforgr.FlowForgr.auth.handlers.impl;

import com.flowforgr.FlowForgr.auth.builders.payload.RoleResponseBuilder;
import com.flowforgr.FlowForgr.auth.builders.record.RoleRecordBuilder;
import com.flowforgr.FlowForgr.auth.entity.AuthIdentity;
import com.flowforgr.FlowForgr.auth.entity.Role;
import com.flowforgr.FlowForgr.auth.enums.UserType;
import com.flowforgr.FlowForgr.auth.handlers.RoleHandler;
import com.flowforgr.FlowForgr.auth.handlers.validation.RoleHandlerValidation;
import com.flowforgr.FlowForgr.auth.payload.request.role.CreateRoleRequest;
import com.flowforgr.FlowForgr.auth.payload.response.auth.CreateRoleResponse;
import com.flowforgr.FlowForgr.auth.records.ValidationResult;
import com.flowforgr.FlowForgr.auth.repo.RoleRepository;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.flowforgr.FlowForgr.shared.util.FlowForgrResponseUtils.createFailureResponse;
import static com.flowforgr.FlowForgr.shared.util.FlowForgrResponseUtils.createSuccessResponse;


@Service
@RequiredArgsConstructor
public class RoleHandlerImpl implements RoleHandler {

    private final RoleHandlerValidation roleHandlerValidation;
    private final RoleRepository roleRepository;


    /**
     * @param request
     * @param authIdentity
     * @return
     */
    @Override
    public ResponseEntity<FlowForgrApiResponse<?>> handleCreateRole(CreateRoleRequest request, AuthIdentity authIdentity) {
        ValidationResult result = roleHandlerValidation.validateRoleCreation(request, authIdentity);
        if(!result.isValid()) {
            return ResponseEntity.status(result.httpStatus()).body(createFailureResponse("Error", result.errorMessage()));
        }
        Role role = RoleRecordBuilder.buildRoleRecordOperation(request, UserType.valueOf(authIdentity.getUserType()));
        roleRepository.save(role);
        CreateRoleResponse response = RoleResponseBuilder.buildCreateRoleResponse(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSuccessResponse(response, "Role created successfully"));
    }
}
