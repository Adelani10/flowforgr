package com.flowforgr.FlowForgr.workflow.builders.record;


import com.flowforgr.FlowForgr.auth.entity.Role;
import com.flowforgr.FlowForgr.shared.entity.AuthIdentity;
import com.flowforgr.FlowForgr.workflow.entity.WorkFlow;
import com.flowforgr.FlowForgr.workflow.entity.WorkFlowStep;
import com.flowforgr.FlowForgr.workflow.enums.WorkFlowState;
import com.flowforgr.FlowForgr.workflow.payload.request.workFlowStep.CreateWorkFlowStepsRequest;
import com.flowforgr.FlowForgr.workflow.payload.request.workflow.CreateWorkFlowRequest;
import org.springframework.util.ObjectUtils;

public class WorkFlowRecordBuilder {

    public static WorkFlowStep buildWorkFlowStepRecord(CreateWorkFlowStepsRequest request, WorkFlowStep workFlowStep, Role role) {
        WorkFlowStep workFlowStepRecord = ObjectUtils.isEmpty(workFlowStep) ? new WorkFlowStep() : workFlowStep;
        workFlowStepRecord.setName(ObjectUtils.isEmpty(request.getStepName()) ? workFlowStepRecord.getName() : request.getStepName());
        workFlowStepRecord.setDescription(ObjectUtils.isEmpty(request.getStepDescription()) ? workFlowStepRecord.getDescription() : request.getStepDescription());
        workFlowStepRecord.setAssignedRole(ObjectUtils.isEmpty(role) ?  workFlowStepRecord.getAssignedRole() : role);
        workFlowStepRecord.setWorkFlowStepIndex(ObjectUtils.isEmpty(request.getWorkFlowStepIndex())  ? workFlowStepRecord.getWorkFlowStepIndex() : request.getWorkFlowStepIndex());
        return workFlowStepRecord;
    }

    public static WorkFlow buildWorkFlowRecord(CreateWorkFlowRequest request, WorkFlow workFlowRecord, AuthIdentity authIdentity) {
        WorkFlow workFlow = ObjectUtils.isEmpty(workFlowRecord) ? new WorkFlow() : workFlowRecord;
        workFlow.setName(ObjectUtils.isEmpty(request.getWorkFlowName()) ? workFlowRecord.getName() : request.getWorkFlowName());
        workFlow.setDescription(ObjectUtils.isEmpty(request.getWorkFlowDescription()) ? workFlowRecord.getDescription() : request.getWorkFlowDescription());
        workFlow.setWorkFlowState(ObjectUtils.isEmpty(request.getWorkFlowState()) ? workFlowRecord.getWorkFlowState() : WorkFlowState.valueOf(request.getWorkFlowState()));
        workFlow.setOrganizationFk(authIdentity.getOrganizationId());
        return workFlow;
    }
}
