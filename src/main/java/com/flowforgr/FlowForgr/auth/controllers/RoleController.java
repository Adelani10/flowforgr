package com.flowforgr.FlowForgr.auth.controllers;


import com.flowforgr.FlowForgr.auth.entity.AuthIdentity;
import com.flowforgr.FlowForgr.auth.handlers.RoleHandler;
import com.flowforgr.FlowForgr.auth.payload.request.role.CreateRoleRequest;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleHandler roleHandler;

    @PostMapping("/create")
    public ResponseEntity<FlowForgrApiResponse<?>> createRole(@RequestBody CreateRoleRequest request, @RequestAttribute("AUTH_IDENTITY") AuthIdentity authIdentity) {
        return roleHandler.handleCreateRole(request, authIdentity);
    }

    @PostMapping("/update")
    public ResponseEntity<FlowForgrApiResponse<?>> updateRole(@RequestBody CreateRoleRequest request, @RequestAttribute("AUTH_IDENTITY") AuthIdentity authIdentity) {
        return roleHandler.handleUpdateRole(request, authIdentity);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FlowForgrApiResponse<?>> deleteRole(@PathVariable Long id, @RequestAttribute("AUTH_IDENTITY") AuthIdentity authIdentity) {
        return roleHandler.deleteRole(id, authIdentity);
    }
}
