package com.sebas.finance.module.financial.domain.repository;

import com.sebas.finance.module.financial.domain.model.Entry;
import com.sebas.finance.module.financial.domain.model.EntryId;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface FinancialEntryRepository {
    Entry save(Entry entry);
    void delete(EntryId id);
    Optional<Entry> findById(EntryId id);
    List<Entry> findByUserIdAndMonth(String userId, YearMonth month);
    List<Entry> findByUserIdAndYear(String userId, int year);
}
