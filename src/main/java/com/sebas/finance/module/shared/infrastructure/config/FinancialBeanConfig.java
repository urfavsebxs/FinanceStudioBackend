package com.sebas.finance.module.shared.infrastructure.config;

import com.sebas.finance.module.financial.application.usecase.*;
import com.sebas.finance.module.financial.domain.repository.FinancialEntryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FinancialBeanConfig {

    @Bean
    public SaveEntryUseCase saveEntryUseCase(FinancialEntryRepository repository) {
        return new SaveEntryUseCase(repository);
    }

    @Bean
    public DeleteEntryUseCase deleteEntryUseCase(FinancialEntryRepository repository) {
        return new DeleteEntryUseCase(repository);
    }

    @Bean
    public FindEntriesByMonthUseCase findEntriesByMonthUseCase(FinancialEntryRepository repository) {
        return new FindEntriesByMonthUseCase(repository);
    }

    @Bean
    public FindEntriesByYearUseCase findEntriesByYearUseCase(FinancialEntryRepository repository) {
        return new FindEntriesByYearUseCase(repository);
    }

    @Bean
    public GetSummaryUseCase getSummaryUseCase(FinancialEntryRepository repository) {
        return new GetSummaryUseCase(repository);
    }
}
