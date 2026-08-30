package com.flowforgr.FlowForgr.workflow.payload.request.workflow;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("roleId")
    private Long roleId;
}
