package com.sebas.finance.module.user.domain.exception;

public class UserNotExitsException extends RuntimeException {
    public UserNotExitsException(String message) {
        super(message);
    }
}
