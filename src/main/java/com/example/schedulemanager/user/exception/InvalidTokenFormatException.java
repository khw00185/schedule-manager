package com.example.schedulemanager.user.exception;

import com.example.schedulemanager.common.exception.CustomException;
import com.example.schedulemanager.common.exception.global.CommonErrorCode;

public class InvalidTokenFormatException extends CustomException {
    public InvalidTokenFormatException() {
        super(CommonErrorCode.InvalidTokenFormat);
    }
}
