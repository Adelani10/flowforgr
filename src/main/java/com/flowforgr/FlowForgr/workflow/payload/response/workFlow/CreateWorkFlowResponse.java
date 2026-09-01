package com.flowforgr.FlowForgr.workflow.payload.response.workFlow;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateWorkFlowResponse {

    @JsonProperty("workFlowName")
    private String workFlowName;

    @JsonProperty("workFlowDescription")
    private String workFlowDescription;

    @JsonProperty("workFlowState")
    private String workFlowState;
}
