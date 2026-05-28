package com.sebas.finance.module.user.infrastructure.mapper;

import com.sebas.finance.module.user.domain.model.*;
import com.sebas.finance.module.user.infrastructure.persistence.UserDocument;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDocument toDocument(User user) {
        UserDocument document = new UserDocument();
        document.setId(user.getUserId().getValue());
        document.setUserFullName(user.getUserFullName().getValue());
        document.setUserEmail(user.getUserEmail().getValue());
        document.setUserPassword(user.getUserPassword().getValue());
        return document;
    }

    public User toDomain(UserDocument document) {
        return new User(
                new UserId(document.getId()),
                new UserFullName(document.getUserFullName()),
                new UserEmail(document.getUserEmail()),
                new UserPassword(document.getUserPassword())
        );
    }
}
