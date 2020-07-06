package com.sns.server.exceptions.post;

import com.sns.server.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class PostNotFoundException extends BaseException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return "작성된 포스트를 찾을 수 없습니다.";
    }
}
