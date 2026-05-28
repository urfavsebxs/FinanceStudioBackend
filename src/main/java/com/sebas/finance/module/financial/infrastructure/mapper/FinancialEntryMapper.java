package com.sebas.finance.module.financial.infrastructure.mapper;

import com.sebas.finance.module.financial.domain.model.*;
import com.sebas.finance.module.financial.infrastructure.persistence.FinancialEntryDocument;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;

@Component
public class FinancialEntryMapper {

    public FinancialEntryDocument toDocument(Entry entry) {
        FinancialEntryDocument document = new FinancialEntryDocument();
        if (entry.getEntryId() != null) {
            document.setId(entry.getEntryId().getValue());
        }
        document.setUserId(entry.getUserId());
        document.setMonth(Date.from(entry.getMonth().getValue()
                .atDay(1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()));
        document.setCategory(entry.getCategory().toLowerCase());
        document.setLabel(entry.getLabel().getValue());
        document.setAmount(entry.getAmount().getValue());
        return document;
    }

    public Entry toDomain(FinancialEntryDocument document) {
        return new Entry(
                new EntryId(document.getId()),
                document.getUserId(),
                new EntryMonth(YearMonth.from(document.getMonth().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())),
                EntryCategory.fromString(document.getCategory()),
                new EntryLabel(document.getLabel()),
                new EntryAmount(document.getAmount())
        );
    }
}
