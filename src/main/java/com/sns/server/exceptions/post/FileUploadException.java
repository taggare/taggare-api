package com.sns.server.exceptions.post;

import com.sns.server.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class FileUploadException extends BaseException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getMessage() {
        return "파일 업로드 중에 에러가 발생하였습니다.";
    }
}
