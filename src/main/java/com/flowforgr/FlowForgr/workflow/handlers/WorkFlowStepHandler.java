package com.flowforgr.FlowForgr.workflow.handlers;

import com.flowforgr.FlowForgr.shared.entity.AuthIdentity;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import com.flowforgr.FlowForgr.workflow.payload.request.workFlowStep.CreateWorkFlowStepsRequest;
import org.springframework.http.ResponseEntity;

public interface WorkFlowStepHandler {
    ResponseEntity<FlowForgrApiResponse<?>> handleCreateWorkFlowStep(CreateWorkFlowStepsRequest request, AuthIdentity authIdentity);
    ResponseEntity<FlowForgrApiResponse<?>> handleUpdateWorkFlowStep(CreateWorkFlowStepsRequest request, AuthIdentity authIdentity);
}
