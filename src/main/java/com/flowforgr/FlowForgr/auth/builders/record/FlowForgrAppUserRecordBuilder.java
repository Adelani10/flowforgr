package com.flowforgr.FlowForgr.auth.builders.record;

import com.flowforgr.FlowForgr.auth.entity.AppUser;
import com.flowforgr.FlowForgr.auth.entity.Organization;
import com.flowforgr.FlowForgr.auth.enums.UserType;
import com.flowforgr.FlowForgr.auth.payload.request.auth.FlowForgrRegisterOrganizationRequest;
import org.springframework.util.ObjectUtils;

public class FlowForgrAppUserRecordBuilder {

    public static AppUser buildOrganizationAppUserRecordOperation(FlowForgrRegisterOrganizationRequest request, String hashedPassword) {
        AppUser appUser = new AppUser();
        appUser.setFirstName(ObjectUtils.isEmpty(request.getFirstName()) ? appUser.getFirstName() : request.getFirstName());
        appUser.setLastName(ObjectUtils.isEmpty(request.getLastName()) ? appUser.getLastName() : request.getLastName());
        appUser.setEmail(ObjectUtils.isEmpty(request.getEmail()) ? appUser.getEmail() : request.getEmail());
        appUser.setPhoneNumber(ObjectUtils.isEmpty(request.getPhoneNumber()) ? appUser.getPhoneNumber() : request.getPhoneNumber());
        appUser.setGender(ObjectUtils.isEmpty(request.getGender()) ? appUser.getGender() : request.getGender());
        appUser.setPassword(ObjectUtils.isEmpty(request.getPassword()) ? appUser.getPassword() : hashedPassword);
        appUser.setUserType(UserType.Client);
        return appUser;
    }

    public static Organization buildOrganizationRecordOperation (FlowForgrRegisterOrganizationRequest request) {
        Organization organization = new Organization();
        organization.setOrgName(ObjectUtils.isEmpty(request.getOrgName()) ? organization.getOrgName() : request.getOrgName());
        organization.setOrgEmail(ObjectUtils.isEmpty(request.getOrgEmail()) ? organization.getOrgEmail() : request.getOrgEmail());
        organization.setOrgPhoneNumber(ObjectUtils.isEmpty(request.getOrgPhoneNumber()) ? organization.getOrgPhoneNumber() : request.getOrgPhoneNumber());
        organization.setOrgAddress(ObjectUtils.isEmpty(request.getOrgAddress()) ? organization.getOrgAddress() : request.getOrgAddress());
        return organization;
    }
}
