package com.sebas.finance.module.financial.infrastructure.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "financial_entries")
public class FinancialEntryDocument {

    @Id
    private String id;
    private String userId;
    private Date month;
    private String category;
    private String label;
    private double amount;
}
