package com.example.schedulemanager.common.exception.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorReason {
    private final HttpStatus status;
    private final String code;
    private final String errorMessage;

    @Builder(access = AccessLevel.PRIVATE)
    private ErrorReason(HttpStatus status, String code, String errorMessage) {
        this.status = status;
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public static ErrorReason of(HttpStatus status, String code, String errorMessage){
        return ErrorReason.builder()
                .status(status)
                .code(code)
                .errorMessage(errorMessage)
                .build();
    }
}
