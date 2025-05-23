package dev.friox.springsecurityjwt.model.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpResponseDto {

    private Long id;
    private String username;

}
