package dev.friox.springsecurityjwt.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RoleErrorCode implements ErrorCode {

    ROLE_NOT_FOUND ("ROLE_0001", "존재하지 않는 역할", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;

}
