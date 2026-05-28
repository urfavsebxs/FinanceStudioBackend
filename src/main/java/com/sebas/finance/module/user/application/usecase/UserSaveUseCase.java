package com.sebas.finance.module.user.application.usecase;

import com.sebas.finance.module.user.domain.exception.UserExistException;
import com.sebas.finance.module.user.domain.model.User;
import com.sebas.finance.module.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSaveUseCase {

    private final UserRepository repository;

    public void execute(User user){
        repository.findById(user.getUserId())
                .ifPresent(existingUser -> {
                    throw new UserExistException("Este usuario ya existe en nuestro sistema");
                });

        this.repository.save(user);
    }
}
