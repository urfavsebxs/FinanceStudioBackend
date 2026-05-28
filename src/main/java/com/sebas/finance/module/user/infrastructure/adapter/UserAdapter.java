package com.sebas.finance.module.user.infrastructure.adapter;

import com.sebas.finance.module.user.domain.model.User;
import com.sebas.finance.module.user.domain.model.UserId;
import com.sebas.finance.module.user.domain.repository.UserRepository;
import com.sebas.finance.module.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAdapter implements UserRepository {

    private final UserMongoRepository mongoRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        var document = userMapper.toDocument(user);
        var savedDocument = mongoRepository.save(document);
        return userMapper.toDomain(savedDocument);
    }

    @Override
    public void update(User user) {
        var document = userMapper.toDocument(user);
        mongoRepository.save(document);
    }

    @Override
    public void delete(UserId id) {
        mongoRepository.deleteById(id.getValue());
    }

    @Override
    public List<User> findAll() {
        return mongoRepository.findAll()
                .stream()
                .map(userMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<User> findById(UserId id) {
        return mongoRepository.findById(id.getValue())
                .map(userMapper::toDomain);
    }
}
