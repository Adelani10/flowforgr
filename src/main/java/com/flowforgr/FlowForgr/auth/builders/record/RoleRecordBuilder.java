package com.flowforgr.FlowForgr.auth.builders.record;

import com.flowforgr.FlowForgr.auth.entity.Role;
import com.flowforgr.FlowForgr.auth.enums.UserType;
import com.flowforgr.FlowForgr.auth.payload.request.role.CreateRoleRequest;
import org.springframework.util.ObjectUtils;

public class RoleRecordBuilder {

    public static Role buildRoleRecordOperation (CreateRoleRequest request, UserType userType) {
        Role role = new Role();
        role.setRoleName(ObjectUtils.isEmpty(request.getRoleName()) ? role.getRoleName() : request.getRoleName());
        role.setDescription(ObjectUtils.isEmpty(request.getDescription()) ? role.getDescription() : request.getDescription());
        role.setStatus(ObjectUtils.isEmpty(request.getRoleStatus()) ? role.isStatus() :  request.getRoleStatus());
        role.setCustom(true);
        role.setUserType(ObjectUtils.isEmpty(userType) ? role.getUserType() : UserType.valueOf(request.getUserType()));
        return role;
    }
}
