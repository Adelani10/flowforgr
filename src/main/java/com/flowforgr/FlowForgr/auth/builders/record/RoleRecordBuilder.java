package com.flowforgr.FlowForgr.auth.builders.record;

import com.flowforgr.FlowForgr.shared.entity.AuthIdentity;
import com.flowforgr.FlowForgr.auth.entity.Role;
import com.flowforgr.FlowForgr.auth.enums.UserType;
import com.flowforgr.FlowForgr.auth.payload.request.role.CreateRoleRequest;
import org.springframework.util.ObjectUtils;

public class RoleRecordBuilder {

    public static Role buildRoleRecordOperation (CreateRoleRequest request, AuthIdentity authIdentity, Role roleRecord) {
        Role role = ObjectUtils.isEmpty(roleRecord) ? new Role() : roleRecord;
        role.setRoleName(ObjectUtils.isEmpty(request.getRoleName()) ? role.getRoleName() : request.getRoleName());
        role.setDescription(ObjectUtils.isEmpty(request.getDescription()) ? role.getDescription() : request.getDescription());
        role.setStatus(ObjectUtils.isEmpty(request.getRoleStatus()) ? role.isStatus() :  request.getRoleStatus());
        role.setOrganizationFk(authIdentity.getOrganizationId());
        role.setUserType(UserType.valueOf(authIdentity.getUserType()));
        role.setCustom(true);
        return role;
    }
}
