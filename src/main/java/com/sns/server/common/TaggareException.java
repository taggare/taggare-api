package com.sns.server.common;


import com.sns.server.enums.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class TaggareException extends RuntimeException {

    private Error[] errors;
    private ErrorCode code;
    private HttpStatus httpStatus;
    private String message;
    private String exceptionMessage;

    public TaggareException(String defaultMessage, String field) {
        this.errors = new Error[]{new Error(defaultMessage, field)};
    }

    /**
     * 1. 폼 검증시 사용하는 ValidCustomException 생성자.
     * <pre>
     * {@code
     *      if(couponRepo.findAll().isEmpty()) {
     *          throw new ValidCustomException(ErrorCode.COUPON_NOT_FIND_ALL);
     *      }
     * }
     * </pre>
     *
     * @param errorCode the error code
     */
    public TaggareException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.message = "[" + errorCode + "] " + errorCode.getMessage();
        this.errors = new Error[]{new Error(this.message, null)};
    }

    /**
     * 1. 세부적인 Exception을 수집하기위한 ValidCustomException 생성자
     * 2. try-catch 구문에서 발생하는 Exception.getMessage()의 리턴 값을 받음.
     * <pre>
     * {@code
     *      try {
     *          ...
     *      } catch (Exception ex) {
     *          throw new ValidCustomException(ErrorCode.COUPON_UNKNOWN_ERROR, ex.getMessage());
     *      }
     * }
     * </pre>
     *
     * @param errorCode
     * @param exceptionMessage 시스템에서 발생하는 에러를 담는 String
     */
    public TaggareException(ErrorCode errorCode, String exceptionMessage) {
        super(errorCode.getMessage());
        this.code = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.message = "에러코드 : [" + errorCode + "]";
        this.exceptionMessage = exceptionMessage;
        this.errors = new Error[]{new Error(this.message, null)};
    }

    public TaggareException(Error[] errors) {
        this.errors = errors;
    }

    public Error[] getErrors() {
        return errors;
    }

    private static class Error {
        private String defaultMessage;
        private String field;

        public Error(String defaultMessage, String field) {
            this.defaultMessage = defaultMessage;
            this.field = field;
        }

        public String getDefaultMessage() {
            return defaultMessage;
        }

        public String getField() {
            return field;
        }
    }
}
