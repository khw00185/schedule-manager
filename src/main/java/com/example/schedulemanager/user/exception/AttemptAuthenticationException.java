package com.example.schedulemanager.user.exception;

import com.example.schedulemanager.common.exception.CustomException;
import com.example.schedulemanager.common.exception.global.CommonErrorCode;

public class AttemptAuthenticationException extends CustomException {
    public AttemptAuthenticationException() {
        super(CommonErrorCode.AttemptAuthentication);
    }
}