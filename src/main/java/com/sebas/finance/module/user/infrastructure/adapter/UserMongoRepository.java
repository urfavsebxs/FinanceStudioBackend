package com.sebas.finance.module.user.infrastructure.adapter;

import com.sebas.finance.module.user.infrastructure.persistence.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<UserDocument, String> {
    Optional<UserDocument> findByUserEmail(String email);
}
