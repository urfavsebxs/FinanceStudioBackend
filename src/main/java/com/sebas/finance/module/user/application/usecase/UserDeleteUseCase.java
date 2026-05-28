package com.sebas.finance.module.user.application.usecase;

import com.sebas.finance.module.user.domain.exception.UserNotExitsException;
import com.sebas.finance.module.user.domain.model.UserId;
import com.sebas.finance.module.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDeleteUseCase {
    private final UserRepository repository;

    public void execute(UserId id){
        if (!repository.findById(id).isPresent()){
            throw new UserNotExitsException("Este usuario no existe en nuestra plataforma");
        }

        this.repository.delete(id);
    }
}
