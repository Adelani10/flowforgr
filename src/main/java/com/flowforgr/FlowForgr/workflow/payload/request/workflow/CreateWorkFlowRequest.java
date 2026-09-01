package com.flowforgr.FlowForgr.workflow.payload.request.workflow;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flowforgr.FlowForgr.workflow.enums.WorkFlowState;
import com.flowforgr.FlowForgr.workflow.payload.request.workFlowStep.CreateWorkFlowStepsRequest;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
public class CreateWorkFlowRequest {

    @JsonProperty("workFlowName")
    private String workFlowName;

    @JsonProperty("workFlowDescription")
    private String workFlowDescription;

    @JsonProperty("workFlowSteps")
    @NotEmpty(message = "At least 1 step is required")
    private List<CreateWorkFlowStepsRequest> workFlowSteps;

    @JsonProperty("workFlowState")
    private String workFlowState;
}


