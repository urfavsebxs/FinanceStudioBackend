package com.sebas.finance.module.auth.infrastructure.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResponse {
    private final String accessToken;
    private final String refreshToken;
}
