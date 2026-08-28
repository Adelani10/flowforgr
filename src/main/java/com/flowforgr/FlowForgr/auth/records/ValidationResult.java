package com.flowforgr.FlowForgr.auth.records;

import org.springframework.http.HttpStatus;

public record ValidationResult(
        boolean isValid,
        String errorMessage,
        HttpStatus httpStatus
) {}
