package com.flowforgr.FlowForgr.auth.payload.request.role;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class CreateRoleRequest {

    @JsonProperty("roleName")
    private String roleName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("roleStatus")
    private Boolean roleStatus;

    @JsonProperty("userType")
    private String userType;
}
