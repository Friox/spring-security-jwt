package dev.friox.springsecurityjwt.model.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignInRequestDto {

    private String username;
    private String password;

}
