package com.sebas.finance.module.auth.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthTokens {
    private final String accessToken;
    private final String refreshToken;
}
