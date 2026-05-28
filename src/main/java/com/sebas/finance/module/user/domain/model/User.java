package com.sebas.finance.module.user.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class User {
    private UserId userId;
    private final UserFullName userFullName;
    private final UserEmail userEmail;
    private final UserPassword userPassword;

    public User(UserId id,
                UserFullName userFullName,
                UserEmail userEmail,
                UserPassword userPassword) {
        this.userId = id;
        this.userFullName = Objects.requireNonNull(userFullName, "El nombre no debe ser nulo");
        this.userEmail = Objects.requireNonNull(userEmail, "El email es obligatorio");
        this.userPassword = Objects.requireNonNull(userPassword, "La contraseña es obligatoria");
    }
}
