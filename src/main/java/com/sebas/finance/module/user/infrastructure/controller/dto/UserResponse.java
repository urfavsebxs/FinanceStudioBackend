package com.sebas.finance.module.user.infrastructure.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String userId;
    private String userFullName;
    private String userEmail;
}
