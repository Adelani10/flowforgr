package com.flowforgr.FlowForgr.auth.builders.response;

import com.flowforgr.FlowForgr.auth.entity.AppUser;
import com.flowforgr.FlowForgr.auth.entity.Organization;
import com.flowforgr.FlowForgr.auth.payload.response.FlowForgrLoginResponse;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrGeneratedAuthToken;

public class FlowForgrAuthPayloadBuilder {

    public static FlowForgrLoginResponse buildFlowForgrLoginResponse(AppUser appUser, Organization org, FlowForgrGeneratedAuthToken flowForgrGeneratedAuthToken) {
        return FlowForgrLoginResponse.builder()
                .firstName(appUser.getFirstName()).lastName(appUser.getLastName()).email(appUser.getEmail())
                .organizationEmail(org.getOrgEmail()).authorityInfos(flowForgrGeneratedAuthToken.getAuthorityInfos())
                .flowForgrGeneratedAuthToken(flowForgrGeneratedAuthToken).organizationName(org.getOrgName())
                .build();
    }
}
