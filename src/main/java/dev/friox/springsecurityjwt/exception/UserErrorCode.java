package dev.friox.springsecurityjwt.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_ALREADY_EXISTS ("USER_0001", "이미 존재하는 사용자", HttpStatus.CONFLICT),
    USER_NOT_FOUND ("USER_0002", "존재하지 않는 사용자", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;

}
