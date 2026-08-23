package com.flowforgr.FlowForgr.shared.payload;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flowforgr.FlowForgr.auth.records.AuthorityInfo;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlowForgrGeneratedAuthToken {
    @JsonProperty("token")
    private String token;

    @JsonProperty("expiredAt")
    private LocalDateTime expiredTime;

    @JsonProperty("tokenType")
    @Builder.Default
    private String tokenType="Bearer";

    @JsonProperty("verificationReference")
    private String verificationReference;

    @JsonProperty("expirationMinutes")
    private long expirationMinutes;

    @JsonIgnore
    private List<AuthorityInfo> authorityInfos;

    @JsonIgnore
    private LocalDateTime creationAt;

    @JsonIgnore
    private LocalDateTime expirationDate;
}
