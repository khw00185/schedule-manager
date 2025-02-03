package com.example.schedulemanager.common.dto;

import com.example.schedulemanager.common.exception.dto.ErrorReason;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"success", "code", "errorMessage", "data"})
public class ResponseDto<T> {
    private final Boolean success;
    private final String code;
    private final String errorMessage;
    private final T data;


    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, HttpStatus.OK.name(), null, data);
    }

    public static <T> ResponseDto<T> fail(String code, String errorMessage) {
        return new ResponseDto<>(false , code , errorMessage, null);
    }

    public static <T> ResponseDto<T> fail(ErrorReason errorReason) {
        return new ResponseDto<>(false , errorReason.getCode() , errorReason.getErrorMessage(), null);
    }
}
