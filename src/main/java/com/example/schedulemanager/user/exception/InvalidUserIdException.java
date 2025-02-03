package com.example.schedulemanager.user.exception;

import com.example.schedulemanager.common.exception.CustomException;
import com.example.schedulemanager.common.exception.global.CommonErrorCode;

public class InvalidUserIdException extends CustomException {
    public InvalidUserIdException() {
      super(CommonErrorCode.InvalidUserId);
    }
}
