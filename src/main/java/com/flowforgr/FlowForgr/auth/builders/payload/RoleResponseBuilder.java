package com.flowforgr.FlowForgr.auth.builders.payload;

import com.flowforgr.FlowForgr.auth.entity.Role;
import com.flowforgr.FlowForgr.auth.payload.response.auth.CreateRoleResponse;

public class RoleResponseBuilder {

    public static CreateRoleResponse buildCreateRoleResponse(Role role) {
        return CreateRoleResponse.builder()
                .roleName(role.getRoleName())
                .roleDescription(role.getDescription())
                .status(role.isStatus() ? "Active": "Inactive").build();

    }
}
