package com.sebas.finance.module.financial.application.usecase;

import com.sebas.finance.module.financial.domain.exception.FutureMonthException;
import com.sebas.finance.module.financial.domain.model.Entry;
import com.sebas.finance.module.financial.domain.model.EntryId;
import com.sebas.finance.module.financial.domain.repository.FinancialEntryRepository;
import lombok.RequiredArgsConstructor;

import java.time.YearMonth;

@RequiredArgsConstructor
public class SaveEntryUseCase {

    private final FinancialEntryRepository repository;

    public Entry execute(Entry entry) {
        YearMonth now = YearMonth.now();
        if (entry.getMonth().getValue().isAfter(now)) {
            throw new FutureMonthException("No puedes registrar meses futuros.");
        }

        if (entry.getEntryId() == null) {
            entry.setEntryId(new EntryId(java.util.UUID.randomUUID().toString()));
        }

        return repository.save(entry);
    }
}
