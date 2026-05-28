package com.sebas.finance.module.auth.application.usecase;

import com.sebas.finance.module.auth.domain.exception.InvalidTokenException;
import com.sebas.finance.module.auth.domain.model.AuthTokens;
import com.sebas.finance.module.auth.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final JwtService jwtService;

    public AuthTokens execute(String refreshToken) {
        if (!jwtService.isTokenValid(refreshToken)) {
            throw new InvalidTokenException("Refresh token invalido o expirado");
        }

        String tokenType = jwtService.extractTokenType(refreshToken);
        if (!"refresh".equals(tokenType)) {
            throw new InvalidTokenException("El token proporcionado no es un refresh token");
        }

        String subject = jwtService.extractSubject(refreshToken);

        String newAccessToken = jwtService.generateAccessToken(subject);
        String newRefreshToken = jwtService.generateRefreshToken(subject);

        return new AuthTokens(newAccessToken, newRefreshToken);
    }
}
