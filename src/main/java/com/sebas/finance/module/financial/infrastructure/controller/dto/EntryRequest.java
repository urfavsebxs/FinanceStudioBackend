package com.sebas.finance.module.financial.infrastructure.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntryRequest {
    private String month;
    private String category;
    private String label;
    private double amount;
}
