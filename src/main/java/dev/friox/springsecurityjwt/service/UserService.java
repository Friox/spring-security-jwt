package dev.friox.springsecurityjwt.service;

import dev.friox.springsecurityjwt.exception.ApiException;
import dev.friox.springsecurityjwt.exception.RoleErrorCode;
import dev.friox.springsecurityjwt.exception.UserErrorCode;
import dev.friox.springsecurityjwt.model.dto.SignInRequestDto;
import dev.friox.springsecurityjwt.model.dto.SignUpRequestDto;
import dev.friox.springsecurityjwt.model.entity.Role;
import dev.friox.springsecurityjwt.model.entity.User;
import dev.friox.springsecurityjwt.repository.RoleRepository;
import dev.friox.springsecurityjwt.repository.UserRepository;
import dev.friox.springsecurityjwt.security.jwt.JwtToken;
import dev.friox.springsecurityjwt.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public User signUp(SignUpRequestDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new ApiException(UserErrorCode.USER_ALREADY_EXISTS);
        }
        String roleName = dto.isAdmin() ? "ADMIN" : "USER";
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ApiException(RoleErrorCode.ROLE_NOT_FOUND));
        User user = dto.toEntity(passwordEncoder, role);
        return userRepository.save(user);
    }

    public JwtToken signIn(SignInRequestDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authentication);
    }
}
