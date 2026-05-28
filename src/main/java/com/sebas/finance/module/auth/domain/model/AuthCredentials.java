package com.sebas.finance.module.auth.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthCredentials {
    private final String email;
    private final String password;
}
