package com.example.schedulemanager.common.exception.global;

import com.example.schedulemanager.common.dto.ResponseDto;
import com.example.schedulemanager.common.exception.CustomException;
import com.example.schedulemanager.common.exception.dto.ErrorReason;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto<?>> handleCustomException(CustomException ex) {
        log.error("CustomException 발생: {}", ex.getErrorReason().getErrorMessage());
        ErrorReason errorReason = ex.getErrorReason();
        return new ResponseEntity<>(ResponseDto.fail(errorReason), errorReason.getStatus());
    }

 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        throw new CustomException(CommonErrorCode.INVALID_INPUT_VALUE);
    }
}
