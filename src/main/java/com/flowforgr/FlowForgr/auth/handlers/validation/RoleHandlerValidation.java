package com.flowforgr.FlowForgr.auth.handlers.validation;


import com.flowforgr.FlowForgr.shared.entity.AuthIdentity;
import com.flowforgr.FlowForgr.auth.payload.request.role.CreateRoleRequest;
import com.flowforgr.FlowForgr.auth.records.ValidationResult;
import com.flowforgr.FlowForgr.auth.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleHandlerValidation {

    private final RoleRepository roleRepository;


    public ValidationResult validateRoleCreation(CreateRoleRequest request, AuthIdentity authIdentity) {
        ValidationResult validationResult = new ValidationResult(true, "", null);

        List<String> roles = authIdentity.getRoles();
        if(ObjectUtils.isEmpty(roles) || roles.stream().noneMatch(role -> role.equalsIgnoreCase("ORGANIZATION_ADMIN"))) {
            validationResult = new ValidationResult(false, "User not authorized", HttpStatus.UNAUTHORIZED);
        }

        if(ObjectUtils.isEmpty(request.getDescription())) {
            validationResult = new ValidationResult(false, "Description is required", HttpStatus.BAD_REQUEST);
        }

        if(isRoleNameAlreadyExistForOrganization(request.getRoleName(), authIdentity.getUserType(), authIdentity.getOrganizationId())) {
            validationResult = new ValidationResult(false, "Role with name already exists", HttpStatus.CONFLICT);
        }
        return validationResult;
    }

    public ValidationResult validateModifyRole(CreateRoleRequest request, AuthIdentity authIdentity) {
        ValidationResult validationResult;
        if(ObjectUtils.isEmpty(request.getId())) {
            validationResult = new ValidationResult(false, "Role Id is required", HttpStatus.BAD_REQUEST);
            return validationResult;
        }
        return validateRoleCreation(request, authIdentity);
    }


    public boolean isRoleNameAlreadyExistForOrganization(String roleName, String userType, Long organizationId) {
        return roleRepository.existsByRoleNameUserTypeAndOrganization(roleName, userType, organizationId);
    }
}
