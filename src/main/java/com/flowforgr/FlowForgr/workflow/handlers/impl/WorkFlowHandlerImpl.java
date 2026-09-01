package com.flowforgr.FlowForgr.workflow.handlers.impl;

import com.flowforgr.FlowForgr.auth.entity.Role;
import com.flowforgr.FlowForgr.auth.records.ValidationResult;
import com.flowforgr.FlowForgr.auth.repo.RoleRepository;
import com.flowforgr.FlowForgr.shared.entity.AuthIdentity;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import com.flowforgr.FlowForgr.workflow.builders.payload.WorkFlowResponseBuilder;
import com.flowforgr.FlowForgr.workflow.builders.record.WorkFlowRecordBuilder;
import com.flowforgr.FlowForgr.workflow.entity.WorkFlow;
import com.flowforgr.FlowForgr.workflow.entity.WorkFlowStep;
import com.flowforgr.FlowForgr.workflow.handlers.WorkFlowHandler;
import com.flowforgr.FlowForgr.workflow.handlers.validation.WorkFlowHandlerValidation;
import com.flowforgr.FlowForgr.workflow.payload.request.workFlowStep.CreateWorkFlowStepsRequest;
import com.flowforgr.FlowForgr.workflow.payload.request.workflow.CreateWorkFlowRequest;
import com.flowforgr.FlowForgr.workflow.payload.response.workFlow.CreateWorkFlowResponse;
import com.flowforgr.FlowForgr.workflow.repo.WorkFlowRepository;
import com.flowforgr.FlowForgr.workflow.repo.WorkFlowStepRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.flowforgr.FlowForgr.shared.util.FlowForgrResponseUtils.createFailureResponse;
import static com.flowforgr.FlowForgr.shared.util.FlowForgrResponseUtils.createSuccessResponse;

@Service
@RequiredArgsConstructor
public class WorkFlowHandlerImpl  implements WorkFlowHandler {

    private final WorkFlowHandlerValidation workFlowHandlerValidation;
    private final RoleRepository roleRepository;
    private final WorkFlowRepository workFlowRepository;
    private final WorkFlowStepRepository workFlowStepRepository;


    @Override
    @Transactional
    public ResponseEntity<FlowForgrApiResponse<?>> handleCreateWorkFlow(CreateWorkFlowRequest request, AuthIdentity authIdentity) {
        List<Long> requestStepRoleIds = request.getWorkFlowSteps().stream().map(CreateWorkFlowStepsRequest::getRoleId).toList();
        List<Role> roleList = roleRepository.findAllById(requestStepRoleIds);
        ValidationResult validationResult = workFlowHandlerValidation.validateWorkFlowCreationRequest(request, authIdentity, roleList, requestStepRoleIds);

        if (!validationResult.isValid()) {
            return ResponseEntity.status(validationResult.httpStatus())
                    .body(createFailureResponse("Error", validationResult.errorMessage()));
        }

        WorkFlow workFlowRecord = WorkFlowRecordBuilder.buildWorkFlowRecord(request, null, authIdentity);
        workFlowRecord = workFlowRepository.save(workFlowRecord);

        List<WorkFlowStep> workFlowStepList = new ArrayList<>();
        Long lastStepIndex = workFlowStepRepository.getLastStepIndex();
        for (Role role : roleList) {
            for (CreateWorkFlowStepsRequest req : request.getWorkFlowSteps()) {
                if(role.getId().equals(req.getRoleId())) {
                    WorkFlowStep workFlowStepRecord = WorkFlowRecordBuilder.buildWorkFlowStepRecord(req, null, role);
                    workFlowStepRecord.setWorkFlowStepIndex(lastStepIndex++);
                    workFlowStepList.add(workFlowStepRecord);
                }
            }
        }
        workFlowRecord.getWorkFlowSteps().addAll(workFlowStepList);
        workFlowRepository.save(workFlowRecord);
        CreateWorkFlowResponse createWorkFlowResponse = WorkFlowResponseBuilder.buildCreateWorkFlowResponse(workFlowRecord);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createSuccessResponse(createWorkFlowResponse, "Work flow created successfully"));
    }
}
