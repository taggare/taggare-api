package com.sns.server.exceptions;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends BaseException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
