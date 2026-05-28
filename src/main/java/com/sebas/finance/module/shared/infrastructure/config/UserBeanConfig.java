package com.sebas.finance.module.shared.infrastructure.config;

import com.sebas.finance.module.user.application.usecase.*;
import com.sebas.finance.module.user.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBeanConfig {

    @Bean
    public UserSaveUseCase userSaveUseCase(UserRepository repository) {
        return new UserSaveUseCase(repository);
    }

    @Bean
    public UserUpdateUseCase userUpdateUseCase(UserRepository repository) {
        return new UserUpdateUseCase(repository);
    }

    @Bean
    public UserDeleteUseCase userDeleteUseCase(UserRepository repository) {
        return new UserDeleteUseCase(repository);
    }

    @Bean
    public UserFindAllUseCase userFindAllUseCase(UserRepository repository) {
        return new UserFindAllUseCase(repository);
    }

    @Bean
    public UserFindByIdUseCase userFindByIdUseCase(UserRepository repository) {
        return new UserFindByIdUseCase(repository);
    }
}
