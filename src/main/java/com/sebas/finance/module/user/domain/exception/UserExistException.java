package com.sebas.finance.module.user.domain.exception;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
