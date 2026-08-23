package com.flowforgr.FlowForgr.shared.util;

import com.flowforgr.FlowForgr.shared.enums.FlowForgrApiRequestType;
import com.flowforgr.FlowForgr.shared.payload.FlowForgrApiResponse;
import org.slf4j.MDC;

import java.time.LocalDateTime;

public class FlowForgrResponseUtils {

    public static <T> FlowForgrApiResponse<T> createSuccessResponse(T data, String message) {
        var time  = LocalDateTime.now();

        MDC.put("requestTime", time.toString());
        MDC.put("data", data.toString());
        MDC.put("message", message);
        return FlowForgrApiResponse.<T>builder()
                .requestTime(time)
                .requestType(FlowForgrApiRequestType.OutBound.name())
                .status(true)
                .message(message)
                .data(data)
                .build();
    }


    public static <T> FlowForgrApiResponse<T> createFailureResponse(String error, String message) {
        var time  = LocalDateTime.now();

        MDC.put("requestTime", time.toString());
        MDC.put("error", error);
        MDC.put("message", message);
        return FlowForgrApiResponse.<T>builder()
                .requestTime(time)
                .requestType(FlowForgrApiRequestType.OutBound.name())
                .status(false)
                .message(message)
                .error(error)
                .build();
    }

}
