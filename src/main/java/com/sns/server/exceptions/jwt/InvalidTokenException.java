package com.sns.server.exceptions.jwt;

import com.sns.server.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BaseException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getMessage() {
        return "올바른 토큰 정보가 아닙니다.";
    }
}
