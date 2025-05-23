package dev.friox.springsecurityjwt.controller;

import dev.friox.springsecurityjwt.model.dto.ApiResponse;
import dev.friox.springsecurityjwt.model.dto.SignInRequestDto;
import dev.friox.springsecurityjwt.model.dto.SignUpRequestDto;
import dev.friox.springsecurityjwt.model.dto.SignUpResponseDto;
import dev.friox.springsecurityjwt.model.entity.User;
import dev.friox.springsecurityjwt.security.jwt.JwtToken;
import dev.friox.springsecurityjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<SignUpResponseDto>> register(@RequestBody SignUpRequestDto dto) {
        User user = userService.signUp(dto);
        SignUpResponseDto result = modelMapper.map(user, SignUpResponseDto.class);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtToken>> login(@RequestBody SignInRequestDto dto) {
        JwtToken token = userService.signIn(dto);
        return ResponseEntity.ok(ApiResponse.success(token));
    }

    @PostMapping("/test")
    public ResponseEntity<ApiResponse<Void>> test() {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/onlyadmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> onlyAdmin() {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/allowall")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Void>> allowAll() {
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
