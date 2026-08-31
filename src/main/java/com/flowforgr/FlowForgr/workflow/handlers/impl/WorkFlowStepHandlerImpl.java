package com.flowforgr.FlowForgr.workflow.handlers.impl;


import com.flowforgr.FlowForgr.auth.entity.Role;
import com.flowforgr.FlowForgr.auth.records.ValidationResult;
import com.flowforgr.FlowForgr.auth.repo.RoleRepository;
import com.flowforgr.FlowForgr.shared.entity.AuthIdentity;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import com.flowforgr.FlowForgr.workflow.builders.record.WorkFlowRecordBuilder;
import com.flowforgr.FlowForgr.workflow.entity.WorkFlowStep;
import com.flowforgr.FlowForgr.workflow.handlers.WorkFlowStepHandler;
import com.flowforgr.FlowForgr.workflow.handlers.validation.WorkFlowHandlerValidation;
import com.flowforgr.FlowForgr.workflow.payload.request.workFlowStep.CreateWorkFlowStepsRequest;
import com.flowforgr.FlowForgr.workflow.repo.WorkFlowStepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static com.flowforgr.FlowForgr.shared.util.FlowForgrResponseUtils.createFailureResponse;
import static com.flowforgr.FlowForgr.shared.util.FlowForgrResponseUtils.createSuccessResponse;

@Service
@RequiredArgsConstructor
public class WorkFlowStepHandlerImpl implements WorkFlowStepHandler {

    private final WorkFlowHandlerValidation workFlowHandlerValidation;
    private final RoleRepository roleRepository;
    private final WorkFlowStepRepository workFlowStepRepository;

    @Override
    public ResponseEntity<FlowForgrApiResponse<?>> handleCreateWorkFlowStep(CreateWorkFlowStepsRequest request, AuthIdentity authIdentity) {
        Role role = roleRepository.findRoleById(request.getRoleId());
        ValidationResult validationResult = workFlowHandlerValidation.validateWorkFlowStepCreationRequest(request, role, authIdentity);

        if(!validationResult.isValid()) {
            return ResponseEntity.status(validationResult.httpStatus()).body(createFailureResponse("Error", validationResult.errorMessage()));
        }
        Long stepIndex = workFlowStepRepository.getLastStepIndex();
        request.setWorkFlowStepIndex(stepIndex + 1);
        WorkFlowStep workFlowStep = WorkFlowRecordBuilder.buildWorkFlowStepRecord(request, null, role);
        workFlowStepRepository.save(workFlowStep);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSuccessResponse("Success", "Work flow step created successfully"));
    }

    @Override
    public ResponseEntity<FlowForgrApiResponse<?>> handleUpdateWorkFlowStep(CreateWorkFlowStepsRequest request, AuthIdentity authIdentity) {
        Role role = roleRepository.findRoleById(request.getRoleId());
        ValidationResult validationResult = workFlowHandlerValidation.validateModifyWorkFlowStepRequest(request, role, authIdentity);

        if(!validationResult.isValid()) {
            return ResponseEntity.status(validationResult.httpStatus()).body(createFailureResponse("Error", validationResult.errorMessage()));
        }
        WorkFlowStep workFlowStep = workFlowStepRepository.findStepById(request.getId());

        if(ObjectUtils.isEmpty(workFlowStep)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createFailureResponse("Error", "Step not found"));
        }
        workFlowStep = WorkFlowRecordBuilder.buildWorkFlowStepRecord(request, workFlowStep, role);
        workFlowStepRepository.save(workFlowStep);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSuccessResponse("Success", "Work flow step updated successfully"));
    }

}
