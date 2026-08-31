package com.flowforgr.FlowForgr.workflow.controllers;


import com.flowforgr.FlowForgr.shared.entity.AuthIdentity;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import com.flowforgr.FlowForgr.workflow.handlers.WorkFlowStepHandler;
import com.flowforgr.FlowForgr.workflow.payload.request.workFlowStep.CreateWorkFlowStepsRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflow/step")
@RequiredArgsConstructor
public class WorkFlowStepController {

    private final WorkFlowStepHandler workFlowStepHandler;

    @PostMapping("/create")
    public ResponseEntity<FlowForgrApiResponse<?>> createFlowStep (@Valid @RequestBody CreateWorkFlowStepsRequest request, @RequestAttribute AuthIdentity authIdentity) {
        return workFlowStepHandler.handleCreateWorkFlowStep(request, authIdentity);
    }

    @PostMapping("/update")
    public ResponseEntity<FlowForgrApiResponse<?>> updateFlowStep (@Valid @RequestBody CreateWorkFlowStepsRequest request, @RequestAttribute AuthIdentity authIdentity) {
        return workFlowStepHandler.handleUpdateWorkFlowStep(request, authIdentity);
    }
}
