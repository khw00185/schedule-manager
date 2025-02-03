package com.example.schedulemanager.schedule.exception;

import com.example.schedulemanager.common.exception.CustomException;
import com.example.schedulemanager.common.exception.global.CommonErrorCode;

public class SchedulePermissionDeniedException extends CustomException{
    public SchedulePermissionDeniedException() {
        super(CommonErrorCode.SchedulePermissionDenied);
    }
}
