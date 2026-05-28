package com.sebas.finance.module.financial.application.usecase;

import com.sebas.finance.module.financial.domain.exception.EntryNotFoundException;
import com.sebas.finance.module.financial.domain.model.EntryId;
import com.sebas.finance.module.financial.domain.repository.FinancialEntryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteEntryUseCase {

    private final FinancialEntryRepository repository;

    public void execute(String userId, EntryId id) {
        var entry = repository.findById(id)
                .orElseThrow(() -> new EntryNotFoundException("Registro no encontrado."));

        if (!entry.getUserId().equals(userId)) {
            throw new EntryNotFoundException("Registro no encontrado.");
        }

        repository.delete(id);
    }
}
