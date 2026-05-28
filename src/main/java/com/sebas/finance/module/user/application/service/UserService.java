package com.sebas.finance.module.user.application.service;

import com.sebas.finance.module.user.domain.model.User;
import com.sebas.finance.module.user.domain.model.UserId;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);
    void update(User user);
    void delete(UserId id);

    List<User> findAll();
    Optional<User> findById(UserId id);
}
