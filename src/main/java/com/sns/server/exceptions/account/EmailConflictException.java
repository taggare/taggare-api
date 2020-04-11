package com.sns.server.exceptions.account;

import com.sns.server.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class EmailConflictException extends BaseException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
