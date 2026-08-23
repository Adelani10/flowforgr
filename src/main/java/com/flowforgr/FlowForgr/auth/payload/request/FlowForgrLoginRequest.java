package com.flowforgr.FlowForgr.auth.payload.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flowforgr.FlowForgr.shared.engine.annotation.MaskField;
import com.flowforgr.FlowForgr.shared.engine.annotation.ValidEmail;
import com.flowforgr.FlowForgr.shared.engine.annotation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class FlowForgrLoginRequest {

    @JsonProperty("username")
    @NotBlank(message = "Username cannot be empty")
    @ValidEmail(message = "Invalid email format")
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "Password cannot be empty")
    @ValidPassword(message = "Invalid password format")
    @MaskField
    private String password;
}
