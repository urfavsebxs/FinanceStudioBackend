package com.sebas.finance.module.auth.infrastructure.adapter;

import com.sebas.finance.module.auth.domain.repository.AuthRepository;
import com.sebas.finance.module.user.domain.model.User;
import com.sebas.finance.module.user.domain.model.UserEmail;
import com.sebas.finance.module.user.infrastructure.adapter.UserMongoRepository;
import com.sebas.finance.module.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthAdapter implements AuthRepository {

    private final UserMongoRepository mongoRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByEmail(UserEmail email) {
        return mongoRepository.findByUserEmail(email.getValue())
                .map(userMapper::toDomain);
    }

    @Override
    public User save(User user) {
        var document = userMapper.toDocument(user);
        var savedDocument = mongoRepository.save(document);
        return userMapper.toDomain(savedDocument);
    }
}
