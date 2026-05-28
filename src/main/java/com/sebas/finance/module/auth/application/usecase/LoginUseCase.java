package com.sebas.finance.module.auth.application.usecase;

import com.sebas.finance.module.auth.domain.exception.InvalidCredentialsException;
import com.sebas.finance.module.auth.domain.model.AuthTokens;
import com.sebas.finance.module.auth.domain.repository.AuthRepository;
import com.sebas.finance.module.auth.infrastructure.security.JwtService;
import com.sebas.finance.module.auth.infrastructure.security.PasswordService;
import com.sebas.finance.module.user.domain.model.UserEmail;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginUseCase {

    private final AuthRepository authRepository;
    private final PasswordService passwordService;
    private final JwtService jwtService;

    public AuthTokens execute(String email, String rawPassword) {
        var user = authRepository.findByEmail(new UserEmail(email))
                .orElseThrow(() -> new InvalidCredentialsException("Credenciales invalidas"));

        if (!passwordService.verify(rawPassword, user.getUserPassword().getValue())) {
            throw new InvalidCredentialsException("Credenciales invalidas");
        }

        String accessToken = jwtService.generateAccessToken(user.getUserId().getValue());
        String refreshToken = jwtService.generateRefreshToken(user.getUserId().getValue());

        return new AuthTokens(accessToken, refreshToken);
    }
}
