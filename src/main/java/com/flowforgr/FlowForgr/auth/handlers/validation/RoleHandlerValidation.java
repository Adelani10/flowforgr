package com.flowforgr.FlowForgr.auth.handlers.validation;


import com.flowforgr.FlowForgr.auth.entity.AuthIdentity;
import com.flowforgr.FlowForgr.auth.enums.UserType;
import com.flowforgr.FlowForgr.auth.payload.request.role.CreateRoleRequest;
import com.flowforgr.FlowForgr.auth.records.ValidationResult;
import com.flowforgr.FlowForgr.auth.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class RoleHandlerValidation {

    private final RoleRepository roleRepository;


    public ValidationResult validateRoleCreation(CreateRoleRequest request, AuthIdentity authIdentity) {
        ValidationResult validationResult = new ValidationResult(true, "", null);

        if(ObjectUtils.isEmpty(request.getDescription())) {
            validationResult = new ValidationResult(false, "Description is required", HttpStatus.BAD_REQUEST);
        }

        if(isRoleNameAlreadyExistForOrganization(request.getRoleName(), authIdentity.getUserType(), authIdentity.getOrganizationId())) {
            validationResult = new ValidationResult(false, "Role with name already exists", HttpStatus.CONFLICT);
        }
        return validationResult;
    }


    public boolean isRoleNameAlreadyExistForOrganization(String roleName, String userType, Long organizationId) {
        return roleRepository.existsByRoleNameUserTypeAndOrganization(roleName, userType, organizationId);
    }
}
