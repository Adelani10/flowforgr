package com.flowforgr.FlowForgr.workflow.builders.record;


import com.flowforgr.FlowForgr.auth.entity.Role;
import com.flowforgr.FlowForgr.workflow.entity.WorkFlowStep;
import com.flowforgr.FlowForgr.workflow.payload.request.workFlowStep.CreateWorkFlowStepsRequest;
import org.springframework.util.ObjectUtils;

public class WorkFlowRecordBuilder {

    public static WorkFlowStep buildWorkFlowStepRecord(CreateWorkFlowStepsRequest request, WorkFlowStep workFlowStep, Role role) {
        WorkFlowStep workFlowStepRecord = ObjectUtils.isEmpty(workFlowStep) ? new WorkFlowStep() : workFlowStep;
        workFlowStepRecord.setName(ObjectUtils.isEmpty(request.getStepName()) ? workFlowStepRecord.getName() : request.getStepName());
        workFlowStepRecord.setDescription(ObjectUtils.isEmpty(request.getStepDescription()) ? workFlowStepRecord.getDescription() : request.getStepDescription());
        workFlowStepRecord.setAssignedRole(ObjectUtils.isEmpty(role) ?  workFlowStepRecord.getAssignedRole() : role);
        workFlowStepRecord.setWorkFlowFk(ObjectUtils.isEmpty(request.getWorkFlowId()) ? workFlowStepRecord.getWorkFlowFk() : request.getWorkFlowId());
        workFlowStepRecord.setWorkFlowStepIndex(ObjectUtils.isEmpty(request.getWorkFlowStepIndex())  ? workFlowStepRecord.getWorkFlowStepIndex() : request.getWorkFlowStepIndex());
        return workFlowStepRecord;
    }
}
