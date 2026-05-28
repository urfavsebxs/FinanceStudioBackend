package com.sebas.finance.module.financial.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.YearMonth;

@RequiredArgsConstructor
@Getter
public class EntryMonth {
    private final YearMonth value;

    public static EntryMonth fromString(String month) {
        String[] parts = month.split("-");
        int year = Integer.parseInt(parts[0]);
        int monthNum = Integer.parseInt(parts[1]);
        return new EntryMonth(YearMonth.of(year, monthNum));
    }

    public String toNormalizedString() {
        return value + "-01";
    }

    public int getYear() {
        return value.getYear();
    }

    public int getMonthValue() {
        return value.getMonthValue();
    }
}
