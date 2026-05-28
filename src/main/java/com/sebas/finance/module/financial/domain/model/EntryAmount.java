package com.sebas.finance.module.financial.domain.model;

import lombok.Getter;

@Getter
public class EntryAmount {
    private final double value;

    public EntryAmount(double value) {
        if (Double.isNaN(value) || value <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0.");
        }
        this.value = value;
    }
}
