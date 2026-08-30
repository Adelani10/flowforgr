package com.flowforgr.FlowForgr.workflow.handlers;

import com.flowforgr.FlowForgr.shared.entity.AuthIdentity;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import com.flowforgr.FlowForgr.workflow.payload.request.workflow.CreateWorkFlowRequest;
import org.springframework.http.ResponseEntity;

public interface WorkFlowHandler {

    ResponseEntity<FlowForgrApiResponse<?>> handleCreateWorkFlow(CreateWorkFlowRequest request, AuthIdentity authIdentity);
}
