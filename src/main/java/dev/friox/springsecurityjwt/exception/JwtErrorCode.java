package dev.friox.springsecurityjwt.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtErrorCode implements ErrorCode {

    EXPIRED ("JWT_0001", "만료된 JWT", HttpStatus.UNAUTHORIZED),
    UNSUPPORTED ("JWT_0002", "지원하지 않는 JWT", HttpStatus.BAD_REQUEST),
    MALFORMED ("JWT_0003", "올바르지 않은 JWT", HttpStatus.BAD_REQUEST),
    SIGNATURE ("JWT_0004", "서명 검증 실패", HttpStatus.UNAUTHORIZED),
    PREMATURE ("JWT_0005", "NBF", HttpStatus.UNAUTHORIZED),
    CLAIM ("JWT_0006", "권한 Claim 검사 실패", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus status;

}
