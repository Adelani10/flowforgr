package com.flowforgr.FlowForgr.workflow.handlers.impl;

import com.flowforgr.FlowForgr.shared.entity.AuthIdentity;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import com.flowforgr.FlowForgr.workflow.handlers.WorkFlowHandler;
import com.flowforgr.FlowForgr.workflow.payload.request.workflow.CreateWorkFlowRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkFlowHandlerImpl  implements WorkFlowHandler {


    @Override
    public ResponseEntity<FlowForgrApiResponse<?>> handleCreateWorkFlow(CreateWorkFlowRequest request, AuthIdentity authIdentity) {
        return null;
    }
}
