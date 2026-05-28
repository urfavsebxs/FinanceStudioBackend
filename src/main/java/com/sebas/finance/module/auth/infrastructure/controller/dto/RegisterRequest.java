package com.sebas.finance.module.auth.infrastructure.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
}
