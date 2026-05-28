package com.sebas.finance.module.user.application.usecase;

import com.sebas.finance.module.user.domain.model.User;
import com.sebas.finance.module.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserFindAllUseCase {
    private final UserRepository repository;

    public List<User> execute(){
        return repository.findAll();
    }
}
