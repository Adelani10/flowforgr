package com.flowforgr.FlowForgr.auth.payload.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flowforgr.FlowForgr.auth.enums.Gender;
import com.flowforgr.FlowForgr.shared.engine.annotation.MaskField;
import com.flowforgr.FlowForgr.shared.engine.annotation.ValidEmail;
import com.flowforgr.FlowForgr.shared.engine.annotation.ValidPassword;
import com.flowforgr.FlowForgr.shared.engine.annotation.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class FlowForgrRegisterOrganizationRequest {

    @JsonProperty("orgName")
    @NotBlank(message = "Organization name cannot be empty")
    private String orgName;

    @JsonProperty("orgEmail")
    @NotBlank(message = "Email cannot be empty")
    @ValidEmail(message = "Invalid email format")
    private String orgEmail;

    @JsonProperty("orgPhoneNumber")
    @NotBlank(message = "Phone number cannot be empty")
    @ValidPhoneNumber(message = "Invalid phone number")
    private String orgPhoneNumber;

    @JsonProperty("orgAddress")
    private String orgAddress;

//    @JsonProperty("verificationToken")
//    @NotBlank(message = "invalid.token.field")
//    private String verificationToken;

    @JsonProperty("firstName")
    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @JsonProperty("lastName")
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @JsonProperty("email")
    @NotBlank(message = "Email cannot be empty")
    @ValidEmail(message = "Invalid email format")
    private String email;

    @JsonProperty("phoneNumber")
    @NotBlank(message = "Phone number cannot be empty")
    @ValidPhoneNumber(message = "Invalid phone number")
    private String phoneNumber;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("password")
    @NotBlank(message = "Password cannot be empty")
    @ValidPassword(message = "Invalid password format")
    @MaskField
    private String password;

    @JsonProperty("confirmPassword")
    @NotBlank(message = "invalid.confirm.password")
    @ValidPassword(message = "Invalid confirm password format")
    @MaskField
    private String confirmPassword;
}
