package com.sebas.finance.module.user.infrastructure.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "users")
public class UserDocument {

    @Id
    private String id;
    private String userFullName;
    private String userEmail;
    private String userPassword;
}
