package com.flowforgr.FlowForgr.auth.entity;

import com.flowforgr.FlowForgr.auth.enums.UserType;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class AuthIdentity {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
    private Boolean emailVerified;
    private String userType;
    private Long organizationId;
}
