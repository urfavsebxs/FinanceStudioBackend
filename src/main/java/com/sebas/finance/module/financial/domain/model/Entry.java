package com.sebas.finance.module.financial.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Entry {
    private EntryId entryId;
    private final String userId;
    private final EntryMonth month;
    private final EntryCategory category;
    private final EntryLabel label;
    private final EntryAmount amount;

    public Entry(EntryId entryId,
                 String userId,
                 EntryMonth month,
                 EntryCategory category,
                 EntryLabel label,
                 EntryAmount amount) {
        this.entryId = entryId;
        this.userId = Objects.requireNonNull(userId, "El userId es obligatorio.");
        this.month = Objects.requireNonNull(month, "El mes es obligatorio.");
        this.category = Objects.requireNonNull(category, "La categoria es obligatoria.");
        this.label = Objects.requireNonNull(label, "El concepto es obligatorio.");
        this.amount = Objects.requireNonNull(amount, "El monto es obligatorio.");
    }
}
