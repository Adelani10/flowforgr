package com.flowforgr.FlowForgr.auth.payload.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flowforgr.FlowForgr.auth.entity.Organization;
import com.flowforgr.FlowForgr.auth.records.AuthorityInfo;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrGeneratedAuthToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlowForgrLoginResponse {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("organizationName")
    private String organizationName;

    @JsonProperty("authorities")
    private List<AuthorityInfo> authorityInfos;

    @JsonProperty("authorizationToken")
    private FlowForgrGeneratedAuthToken flowForgrGeneratedAuthToken;

    @JsonProperty("organizationEmail")
    private String organizationEmail;
}
