package com.example.schedulemanager.user.exception;

import com.example.schedulemanager.common.exception.CustomException;
import com.example.schedulemanager.common.exception.global.CommonErrorCode;

public class DuplicatedIdException extends CustomException {
    public DuplicatedIdException() {
        super(CommonErrorCode.DuplicatedId);
    }
}
