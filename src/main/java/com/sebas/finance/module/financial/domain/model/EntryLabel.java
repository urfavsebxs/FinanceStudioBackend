package com.sebas.finance.module.financial.domain.model;

import lombok.Getter;

@Getter
public class EntryLabel {
    private final String value;

    public EntryLabel(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El concepto es obligatorio.");
        }
        if (value.trim().length() > 60) {
            throw new IllegalArgumentException("El concepto no debe exceder 60 caracteres.");
        }
        this.value = value.trim();
    }
}
