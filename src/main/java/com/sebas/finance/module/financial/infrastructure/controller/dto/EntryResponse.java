package com.sebas.finance.module.financial.infrastructure.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EntryResponse {
    private String id;
    private String month;
    private String category;
    private String label;
    private double amount;
}
