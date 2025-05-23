package dev.friox.springsecurityjwt.model.dto;

import dev.friox.springsecurityjwt.model.entity.Role;
import dev.friox.springsecurityjwt.model.entity.User;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequestDto {

    private String username;
    private String password;
    private boolean admin;

    public User toEntity(PasswordEncoder encoder, Role role) {
        return User.builder()
                .username(username)
                .password(encoder.encode(password))
                .roles(Set.of(role))
                .build();
    }
}
