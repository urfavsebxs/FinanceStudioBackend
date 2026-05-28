package com.sebas.finance.module.financial.infrastructure.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SummaryResponse {
    private Totals totals;
    private Breakdown breakdown;
    private int rate;
    private double grandTotal;

    @Getter
    @Setter
    public static class Totals {
        private double income;
        private double expense;
        private double savings;
    }

    @Getter
    @Setter
    public static class Breakdown {
        private List<BreakdownItem> income;
        private List<BreakdownItem> expense;
        private List<BreakdownItem> savings;
    }

    @Getter
    @Setter
    public static class BreakdownItem {
        private String label;
        private double amount;
    }
}
