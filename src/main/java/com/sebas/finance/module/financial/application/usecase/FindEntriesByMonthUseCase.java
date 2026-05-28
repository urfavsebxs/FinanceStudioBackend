package com.sebas.finance.module.financial.application.usecase;

import com.sebas.finance.module.financial.domain.model.Entry;
import com.sebas.finance.module.financial.domain.model.EntryMonth;
import com.sebas.finance.module.financial.domain.repository.FinancialEntryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindEntriesByMonthUseCase {

    private final FinancialEntryRepository repository;

    public List<Entry> execute(String userId, EntryMonth month) {
        return repository.findByUserIdAndMonth(userId, month.getValue());
    }
}
