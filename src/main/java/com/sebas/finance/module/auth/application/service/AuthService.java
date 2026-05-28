package com.sebas.finance.module.auth.application.service;

import com.sebas.finance.module.auth.domain.model.AuthTokens;

public interface AuthService {
    AuthTokens register(String fullName, String email, String password);
    AuthTokens login(String email, String password);
    AuthTokens refresh(String refreshToken);
}
