package com.sns.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND_USER("NOT_FOUND_USER", HttpStatus.NOT_FOUND, "확인할 수 없는 유저입니다."),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "시스템상에 알 수 없는 에러가 발생하였습니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

}
