package com.flowforgr.FlowForgr.auth.records;

public record ValidationResult(
        boolean isValid,
        String errorMessage
) {}
