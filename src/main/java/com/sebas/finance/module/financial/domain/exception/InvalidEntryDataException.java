package com.sebas.finance.module.financial.domain.exception;

public class InvalidEntryDataException extends RuntimeException {
    public InvalidEntryDataException(String message) {
        super(message);
    }
}
