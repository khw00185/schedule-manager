package com.example.schedulemanager.user.exception;

import com.example.schedulemanager.common.exception.CustomException;
import com.example.schedulemanager.common.exception.global.CommonErrorCode;

public class InvalidPasswordException extends CustomException {
    public InvalidPasswordException() {
        super(CommonErrorCode.InvalidPassword);
    }
}
