package com.flowforgr.FlowForgr.workflow.handlers;

import com.flowforgr.FlowForgr.shared.entity.AuthIdentity;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import org.springframework.http.ResponseEntity;

public class WorkFlowHandler {

    public ResponseEntity<FlowForgrApiResponse<?>> handleCreateWorkFlow(CreateWorkFlowRequest request, AuthIdentity authIdentity) {
    }
}
