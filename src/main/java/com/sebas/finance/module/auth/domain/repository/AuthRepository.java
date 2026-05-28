package com.sebas.finance.module.auth.domain.repository;

import com.sebas.finance.module.user.domain.model.User;
import com.sebas.finance.module.user.domain.model.UserEmail;

import java.util.Optional;

public interface AuthRepository {
    Optional<User> findByEmail(UserEmail email);
    User save(User user);
}
