package com.flowforgr.FlowForgr.workflow.handlers.validation;


import com.flowforgr.FlowForgr.auth.entity.Role;
import com.flowforgr.FlowForgr.auth.records.ValidationResult;
import com.flowforgr.FlowForgr.auth.repo.RoleRepository;
import com.flowforgr.FlowForgr.shared.entity.AuthIdentity;
import com.flowforgr.FlowForgr.shared.util.FlowForgrStringUtil;
import com.flowforgr.FlowForgr.workflow.payload.request.workFlowStep.CreateWorkFlowStepsRequest;
import com.flowforgr.FlowForgr.workflow.payload.request.workflow.CreateWorkFlowRequest;
import com.flowforgr.FlowForgr.workflow.repo.WorkFlowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkFlowHandlerValidation {

    private final RoleRepository roleRepository;
    private final WorkFlowRepository workFlowRepository;


    public ValidationResult validateWorkFlowStepCreationRequest(CreateWorkFlowStepsRequest request, Role role, AuthIdentity authIdentity) {
        ValidationResult validationResult = new ValidationResult(true, "", HttpStatus.OK);

        List<String> roles = authIdentity.getRoles();
        if(ObjectUtils.isEmpty(roles) || roles.stream().noneMatch(r -> r.equalsIgnoreCase("ORGANIZATION_ADMIN"))) {
            validationResult = new ValidationResult(false, "User not authorized", HttpStatus.UNAUTHORIZED);
        }

        if(ObjectUtils.isEmpty(role)) {
            validationResult = new ValidationResult(false, "Role not found", HttpStatus.BAD_REQUEST);
        }

        if(FlowForgrStringUtil.isBlank(request.getStepName())) {
            validationResult = new ValidationResult(false, "Step name is required", HttpStatus.BAD_REQUEST);
        }

        if(ObjectUtils.isEmpty(request.getWorkFlowId())) {
            validationResult = new ValidationResult(false, "Work Flow Id is required", HttpStatus.BAD_REQUEST);
        }

        if(!isWorkFlowExistsById(request.getWorkFlowId())) {
            validationResult = new ValidationResult(false, "Work Flow not found", HttpStatus.BAD_REQUEST);
        }

        return validationResult;
    }


    public boolean isWorkFlowExistsById(Long workFlowId) {
        return workFlowRepository.existsById(workFlowId);
    }

    public boolean isRoleExists(Long roleId) {
        return roleRepository.existsById(roleId);
    }

    public ValidationResult validateModifyWorkFlowStepRequest(CreateWorkFlowStepsRequest request, Role role, AuthIdentity authIdentity) {
        ValidationResult validationResult;
        if(ObjectUtils.isEmpty(request.getId())) {
            validationResult = new ValidationResult(false, "Step Id is required", HttpStatus.BAD_REQUEST);
            return validationResult;
        }
        return validateWorkFlowStepCreationRequest(request, role, authIdentity);
    }

    public ValidationResult validateWorkFlowCreationRequest(CreateWorkFlowRequest request, AuthIdentity authIdentity, List<Role> roleList, List<Long> requestStepRoleIds) {
        ValidationResult result = new ValidationResult(true, "", HttpStatus.OK);

        List<String> roles = authIdentity.getRoles();
        if(ObjectUtils.isEmpty(roles) || roles.stream().noneMatch(r -> r.equalsIgnoreCase("ORGANIZATION_ADMIN"))) {
            result = new ValidationResult(false, "User not authorized", HttpStatus.UNAUTHORIZED);
        }

        if(FlowForgrStringUtil.isBlank(request.getWorkFlowName())) {
            result = new ValidationResult(false, "Work flow name is required", HttpStatus.BAD_REQUEST);
        }

        if(ObjectUtils.isEmpty(request.getWorkFlowSteps())) {
            result = new ValidationResult(false, "At least 1 valid step is required", HttpStatus.BAD_REQUEST);
        }

        List<String> requestStepNames = request.getWorkFlowSteps().stream().map(CreateWorkFlowStepsRequest::getStepName).toList();

        for (String stepName : requestStepNames) {
            if(FlowForgrStringUtil.isBlank(stepName)) {
                result = new ValidationResult(false, "Step name is required", HttpStatus.BAD_REQUEST);
            }
        }

        if(ObjectUtils.isEmpty(roleList)) {
            result = new ValidationResult(false, "At least 1 valid step is required", HttpStatus.BAD_REQUEST);
        }

        if(roleList.size() != requestStepRoleIds.size()) {
            result = new ValidationResult(false, "Invalid step detected", HttpStatus.BAD_REQUEST);
        }

        return result;
    }
}
