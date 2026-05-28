package com.sebas.finance.module.financial.application.service;

import com.sebas.finance.module.financial.application.usecase.*;
import com.sebas.finance.module.financial.domain.model.Entry;
import com.sebas.finance.module.financial.domain.model.EntryId;
import com.sebas.finance.module.financial.domain.model.EntryMonth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialEntryServiceImpl implements FinancialEntryService {

    private final SaveEntryUseCase saveEntryUseCase;
    private final DeleteEntryUseCase deleteEntryUseCase;
    private final FindEntriesByMonthUseCase findEntriesByMonthUseCase;
    private final FindEntriesByYearUseCase findEntriesByYearUseCase;
    private final GetSummaryUseCase getSummaryUseCase;

    @Override
    @Transactional
    public Entry save(Entry entry) {
        return saveEntryUseCase.execute(entry);
    }

    @Override
    @Transactional
    public void delete(String userId, EntryId id) {
        deleteEntryUseCase.execute(userId, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entry> findByMonth(String userId, EntryMonth month) {
        return findEntriesByMonthUseCase.execute(userId, month);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entry> findByYear(String userId, int year) {
        return findEntriesByYearUseCase.execute(userId, year);
    }

    @Override
    @Transactional(readOnly = true)
    public GetSummaryUseCase.SummaryResult getSummary(String userId, YearMonth month, boolean annual) {
        return getSummaryUseCase.execute(userId, month, annual);
    }
}
