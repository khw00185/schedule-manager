package com.example.schedulemanager.schedule.exception;

import com.example.schedulemanager.common.exception.CustomException;
import com.example.schedulemanager.common.exception.global.CommonErrorCode;

public class AuthorNotFoundException extends CustomException {
    public AuthorNotFoundException() {
        super(CommonErrorCode.AuthorNotFound);
    }
}
