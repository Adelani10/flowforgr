package com.flowforgr.FlowForgr.auth.handlers.impl;

import com.flowforgr.FlowForgr.auth.builders.payload.RoleResponseBuilder;
import com.flowforgr.FlowForgr.auth.builders.record.RoleRecordBuilder;
import com.flowforgr.FlowForgr.auth.entity.AuthIdentity;
import com.flowforgr.FlowForgr.auth.entity.Role;
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
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

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
        Role role = RoleRecordBuilder.buildRoleRecordOperation(request, authIdentity, null);
        roleRepository.save(role);
        CreateRoleResponse response = RoleResponseBuilder.buildCreateRoleResponse(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSuccessResponse(response, "Role created successfully"));
    }

    @Override
    public ResponseEntity<FlowForgrApiResponse<?>> handleUpdateRole(CreateRoleRequest request, AuthIdentity authIdentity) {
        ValidationResult result = roleHandlerValidation.validateModifyRole(request, authIdentity);

        if(!result.isValid()) {
            return ResponseEntity.status(result.httpStatus()).body(createFailureResponse("Error", result.errorMessage()));
        }

        Role roleRecord = roleRepository.findRoleById(request.getId());
        if(ObjectUtils.isEmpty(roleRecord)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createFailureResponse("Error", "Role not found"));
        }

        Role role = RoleRecordBuilder.buildRoleRecordOperation(request, authIdentity, roleRecord);
        roleRepository.save(role);
        CreateRoleResponse response = RoleResponseBuilder.buildCreateRoleResponse(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSuccessResponse(response, "Role updated successfully"));
    }

    @Override
    public ResponseEntity<FlowForgrApiResponse<?>> deleteRole(Long id, AuthIdentity authIdentity) {
        List<String> roles = authIdentity.getRoles();
        if(ObjectUtils.isEmpty(roles) || roles.stream().noneMatch(role -> role.equalsIgnoreCase("ORGANIZATION_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createFailureResponse("Error", "User not authorized"));
        }
        Role roleRecord = roleRepository.findRoleById(id);
        if(ObjectUtils.isEmpty(roleRecord)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createFailureResponse("Error", "Role not found"));
        }
        roleRecord.setDeleted(true);
        roleRepository.save(roleRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSuccessResponse("Success", "Role deleted successfully"));

    }
}
