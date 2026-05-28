package com.sebas.finance.module.shared.infrastructure.config;

import com.sebas.finance.module.auth.application.usecase.LoginUseCase;
import com.sebas.finance.module.auth.application.usecase.RefreshTokenUseCase;
import com.sebas.finance.module.auth.application.usecase.RegisterUseCase;
import com.sebas.finance.module.auth.domain.repository.AuthRepository;
import com.sebas.finance.module.auth.infrastructure.security.JwtService;
import com.sebas.finance.module.auth.infrastructure.security.PasswordService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthBeanConfig {

    @Bean
    public RegisterUseCase registerUseCase(AuthRepository authRepository,
                                           PasswordService passwordService,
                                           JwtService jwtService) {
        return new RegisterUseCase(authRepository, passwordService, jwtService);
    }

    @Bean
    public LoginUseCase loginUseCase(AuthRepository authRepository,
                                     PasswordService passwordService,
                                     JwtService jwtService) {
        return new LoginUseCase(authRepository, passwordService, jwtService);
    }

    @Bean
    public RefreshTokenUseCase refreshTokenUseCase(JwtService jwtService) {
        return new RefreshTokenUseCase(jwtService);
    }
}
