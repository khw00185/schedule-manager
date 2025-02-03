package com.example.schedulemanager.common.exception;

import com.example.schedulemanager.common.exception.dto.ErrorReason;

public interface BaseErrorCode {
    ErrorReason getErrorReason();
}
