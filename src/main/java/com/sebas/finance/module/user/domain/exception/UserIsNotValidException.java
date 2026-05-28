package com.sebas.finance.module.user.domain.exception;

public class UserIsNotValidException extends RuntimeException {
    public UserIsNotValidException(String message) {
        super(message);
    }
}
