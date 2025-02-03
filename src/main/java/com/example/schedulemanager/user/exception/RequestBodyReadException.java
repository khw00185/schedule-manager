package com.example.schedulemanager.user.exception;

import com.example.schedulemanager.common.exception.CustomException;
import com.example.schedulemanager.common.exception.global.CommonErrorCode;

public class RequestBodyReadException extends CustomException {
    public RequestBodyReadException() {
        super(CommonErrorCode.RequestBodyRead);
    }
}
