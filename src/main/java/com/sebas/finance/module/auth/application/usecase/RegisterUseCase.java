package com.sebas.finance.module.auth.application.usecase;

import com.sebas.finance.module.auth.domain.exception.EmailAlreadyExistsException;
import com.sebas.finance.module.auth.domain.model.AuthTokens;
import com.sebas.finance.module.auth.domain.repository.AuthRepository;
import com.sebas.finance.module.auth.infrastructure.security.JwtService;
import com.sebas.finance.module.auth.infrastructure.security.PasswordService;
import com.sebas.finance.module.user.domain.model.*;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class RegisterUseCase {

    private final AuthRepository authRepository;
    private final PasswordService passwordService;
    private final JwtService jwtService;

    public AuthTokens execute(String fullName, String email, String rawPassword) {
        UserEmail userEmail = new UserEmail(email);

        authRepository.findByEmail(userEmail)
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException("El correo electronico ya esta registrado");
                });

        String hashedPassword = passwordService.hash(rawPassword);

        User user = new User(
                new UserId(UUID.randomUUID().toString()),
                new UserFullName(fullName),
                userEmail,
                new UserPassword(hashedPassword)
        );

        authRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user.getUserId().getValue());
        String refreshToken = jwtService.generateRefreshToken(user.getUserId().getValue());

        return new AuthTokens(accessToken, refreshToken);
    }
}
