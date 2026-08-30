package com.flowforgr.FlowForgr.workflow.controllers;


import com.flowforgr.FlowForgr.shared.entity.AuthIdentity;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import com.flowforgr.FlowForgr.workflow.handlers.WorkFlowHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflow")
@RequiredArgsConstructor
public class WorkFlowController {

    private final WorkFlowHandler workFlowHandler;

    @PostMapping("/create")
    public ResponseEntity<FlowForgrApiResponse<?>> createFlow (@Valid @RequestBody CreateWorkFlowRequest request,
                                                               @RequestAttribute("AUTH_IDENTITY")AuthIdentity authIdentity) {
        return workFlowHandler.handleCreateWorkFlow(request, authIdentity);
    }
}
