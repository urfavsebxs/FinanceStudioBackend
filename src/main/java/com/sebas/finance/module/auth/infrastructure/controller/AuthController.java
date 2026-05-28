package com.sebas.finance.module.auth.infrastructure.controller;

import com.sebas.finance.module.auth.application.service.AuthService;
import com.sebas.finance.module.auth.infrastructure.controller.dto.AuthResponse;
import com.sebas.finance.module.auth.infrastructure.controller.dto.LoginRequest;
import com.sebas.finance.module.auth.infrastructure.controller.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        var tokens = authService.register(
                request.getFullName(),
                request.getEmail(),
                request.getPassword()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(tokens.getAccessToken(), tokens.getRefreshToken()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        var tokens = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(tokens.getAccessToken(), tokens.getRefreshToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestParam String refreshToken) {
        var tokens = authService.refresh(refreshToken);
        return ResponseEntity.ok(new AuthResponse(tokens.getAccessToken(), tokens.getRefreshToken()));
    }
}
