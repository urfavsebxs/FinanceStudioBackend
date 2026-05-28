package com.sebas.finance.module.user.application.service;

import com.sebas.finance.module.user.application.usecase.*;
import com.sebas.finance.module.user.domain.model.User;
import com.sebas.finance.module.user.domain.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserSaveUseCase userSaveUseCase;
    private final UserUpdateUseCase userUpdateUseCase;
    private final UserDeleteUseCase userDeleteUseCase;
    private final UserFindByIdUseCase userFindByIdUseCase;
    private final UserFindAllUseCase userFindAllUseCase;

    @Override
    @Transactional
    public void save(User user){
        userSaveUseCase.execute(user);
    }

    @Override
    @Transactional
    public void update(User user){
        userUpdateUseCase.execute(user);
    }

    @Override
    @Transactional
    public void delete(UserId id) {
        userDeleteUseCase.execute(id);
    }

    @Override
    @Transactional (readOnly = true)
    public Optional<User> findById(UserId id) {
        return userFindByIdUseCase.execute(id);
    }

    @Override
    @Transactional (readOnly = true)
    public List<User> findAll() {
        return userFindAllUseCase.execute();
    }
}
