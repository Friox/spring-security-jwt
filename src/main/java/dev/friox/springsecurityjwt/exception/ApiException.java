package dev.friox.springsecurityjwt.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(ErrorCode errorCode, Throwable e) {
        super(errorCode.getMessage(), e);
        this.errorCode = errorCode;
    }
}
