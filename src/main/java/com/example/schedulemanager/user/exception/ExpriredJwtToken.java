package com.example.schedulemanager.user.exception;

import com.example.schedulemanager.common.exception.CustomException;
import com.example.schedulemanager.common.exception.global.CommonErrorCode;

public class ExpriredJwtToken extends CustomException {
    public ExpriredJwtToken() {
        super(CommonErrorCode.TOKEN_EXPIRED);
    }
}
