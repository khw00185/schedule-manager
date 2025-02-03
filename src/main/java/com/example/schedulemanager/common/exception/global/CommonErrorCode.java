package com.example.schedulemanager.common.exception.global;

import com.example.schedulemanager.common.exception.BaseErrorCode;
import com.example.schedulemanager.common.exception.dto.ErrorReason;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
@Getter
public enum CommonErrorCode implements BaseErrorCode {

    //schedule관련
    SchedulePermissionDenied(HttpStatus.FORBIDDEN, "SCHEDULE_001", "작성자의 권한이 없습니다."),
    ScheduleNotFound(HttpStatus.NOT_FOUND, "SCHEDULE_002", "해당 일정이 존재하지 않습니다."),
    AuthorNotFound(HttpStatus.NOT_FOUND, "SCHEDULE_003", "해당 작성자의 일정이 존재하지 않습니다."),


    //user관련
    InvalidUserId(HttpStatus.BAD_REQUEST, "USER_001", "ID가 일치하지 않습니다."),
    InvalidPassword(HttpStatus.BAD_REQUEST, "USER_002", "비밀번호가 일치하지 않습니다."),
    DuplicatedId(HttpStatus.CONFLICT, "USER_003", "이미 존재하는 ID입니다."),

    //jwt토큰 관련
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH_001", "토큰이 만료되었습니다."),
    InvalidTokenFormat(HttpStatus.BAD_REQUEST, "AUTH_002", "잘못된 형식의 토큰입니다."),

    //요청 관련
    RequestBodyRead(HttpStatus.BAD_REQUEST, "REQUEST_001", "요청 본문을 읽는 중 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String errorMessage;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status,code,errorMessage);
    }
}
