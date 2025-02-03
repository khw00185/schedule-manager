package com.example.schedulemanager.common.exception;

import com.example.schedulemanager.common.exception.dto.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final BaseErrorCode code;

    public ErrorReason getErrorReason() {
        return this.code.getErrorReason();
    }
}
