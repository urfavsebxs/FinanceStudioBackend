package com.sebas.finance.module.auth.application.service;

import com.sebas.finance.module.auth.application.usecase.LoginUseCase;
import com.sebas.finance.module.auth.application.usecase.RefreshTokenUseCase;
import com.sebas.finance.module.auth.application.usecase.RegisterUseCase;
import com.sebas.finance.module.auth.domain.model.AuthTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @Override
    @Transactional
    public AuthTokens register(String fullName, String email, String password) {
        return registerUseCase.execute(fullName, email, password);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthTokens login(String email, String password) {
        return loginUseCase.execute(email, password);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthTokens refresh(String refreshToken) {
        return refreshTokenUseCase.execute(refreshToken);
    }
}
