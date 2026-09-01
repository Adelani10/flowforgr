package com.flowforgr.FlowForgr.workflow.builders.payload;

import com.flowforgr.FlowForgr.workflow.entity.WorkFlow;
import com.flowforgr.FlowForgr.workflow.payload.response.workFlow.CreateWorkFlowResponse;

public class WorkFlowResponseBuilder {

    public static CreateWorkFlowResponse buildCreateWorkFlowResponse(WorkFlow workFlowRecord) {

        return CreateWorkFlowResponse.builder()
                .workFlowName(workFlowRecord.getName())
                .workFlowDescription(workFlowRecord.getDescription())
                .workFlowState(workFlowRecord.getWorkFlowState().name())
                .build();
    }
}
