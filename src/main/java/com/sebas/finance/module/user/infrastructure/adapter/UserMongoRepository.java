package com.sebas.finance.module.user.infrastructure.adapter;

import com.sebas.finance.module.user.infrastructure.persistence.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<UserDocument, String> {
}
