package com.sebas.finance.module.user.application.usecase;

import com.sebas.finance.module.user.domain.model.User;
import com.sebas.finance.module.user.domain.model.UserId;
import com.sebas.finance.module.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserFindByIdUseCase {
    private final UserRepository repository;

    public Optional<User> execute(UserId id) {
        if (id == null) {
            return Optional.empty();
        }

        return repository.findById(id);
    }
}
