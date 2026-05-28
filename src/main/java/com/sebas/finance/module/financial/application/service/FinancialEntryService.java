package com.sebas.finance.module.financial.application.service;

import com.sebas.finance.module.financial.application.usecase.GetSummaryUseCase;
import com.sebas.finance.module.financial.domain.model.Entry;
import com.sebas.finance.module.financial.domain.model.EntryId;
import com.sebas.finance.module.financial.domain.model.EntryMonth;

import java.time.YearMonth;
import java.util.List;

public interface FinancialEntryService {
    Entry save(Entry entry);
    void delete(String userId, EntryId id);
    List<Entry> findByMonth(String userId, EntryMonth month);
    List<Entry> findByYear(String userId, int year);
    GetSummaryUseCase.SummaryResult getSummary(String userId, YearMonth month, boolean annual);
}
