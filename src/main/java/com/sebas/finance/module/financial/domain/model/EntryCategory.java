package com.sebas.finance.module.financial.domain.model;

public enum EntryCategory {
    INCOME,
    EXPENSE,
    SAVINGS;

    public static EntryCategory fromString(String value) {
        return switch (value.toLowerCase()) {
            case "income" -> INCOME;
            case "expense" -> EXPENSE;
            case "savings" -> SAVINGS;
            default -> throw new IllegalArgumentException("Categoria invalida: " + value);
        };
    }

    public String toLowerCase() {
        return name().toLowerCase();
    }
}
