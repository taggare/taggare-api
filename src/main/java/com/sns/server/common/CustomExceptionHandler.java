package com.sns.server.common;

import com.sns.server.exceptions.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleApiException(BaseException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(exception.getMessage());
    }
}
