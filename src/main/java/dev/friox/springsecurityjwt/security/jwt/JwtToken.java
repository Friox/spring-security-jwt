package dev.friox.springsecurityjwt.security.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtToken {

    private String accessToken;
    private String refreshToken;

}
