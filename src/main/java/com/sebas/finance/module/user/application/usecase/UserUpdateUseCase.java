package com.sebas.finance.module.user.application.usecase;

import com.sebas.finance.module.user.domain.exception.UserNotExitsException;
import com.sebas.finance.module.user.domain.model.User;
import com.sebas.finance.module.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserUpdateUseCase {

    private final UserRepository repository;

    public void execute(User user){
        if (user.getUserId() == null || repository.findById(user.getUserId()).isEmpty()){
            throw new UserNotExitsException("No se puede actualizar: Usuario con ID "
                    + (user.getUserId() != null ? user.getUserId().getValue() : "null")
                    + " no encontrado.");
        }

        this.repository.update(user);
    }

}
