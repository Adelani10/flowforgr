package com.flowforgr.FlowForgr.workflow.payload.request.workFlowStep;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
public class CreateWorkFlowStepsRequest {

    @JsonProperty("stepName")
    private String stepName;

    @JsonProperty("stepDescription")
    private String stepDescription;

    @NotNull(message = "Role id is required")
    @JsonProperty("roleId")
    private Long roleId;

    @JsonProperty("workFlowId")
    private Long workFlowId;

    @JsonProperty("id")
    private Long id;

    @JsonIgnore
    private Long workFlowStepIndex;
}
