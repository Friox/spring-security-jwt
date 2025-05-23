package dev.friox.springsecurityjwt.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    REQUIRE_AUTHENTICATION ("AUTH_0001", "인증이 필요합니다", HttpStatus.UNAUTHORIZED),
    REQUIRE_PERMISSION ("AUTH_0002", "권한이 없습니다", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;

}
