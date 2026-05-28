package com.sebas.finance.module.financial.application.usecase;

import com.sebas.finance.module.financial.domain.model.Entry;
import com.sebas.finance.module.financial.domain.model.EntryCategory;
import com.sebas.finance.module.financial.domain.repository.FinancialEntryRepository;
import lombok.RequiredArgsConstructor;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetSummaryUseCase {

    private final FinancialEntryRepository repository;

    public SummaryResult execute(String userId, YearMonth month, boolean annual) {
        List<Entry> entries;
        if (annual) {
            entries = repository.findByUserIdAndYear(userId, month.getYear());
        } else {
            entries = repository.findByUserIdAndMonth(userId, month);
        }

        Map<EntryCategory, Double> totals = new EnumMap<>(EntryCategory.class);
        totals.put(EntryCategory.INCOME, 0.0);
        totals.put(EntryCategory.EXPENSE, 0.0);
        totals.put(EntryCategory.SAVINGS, 0.0);

        Map<EntryCategory, Map<String, Double>> breakdown = new EnumMap<>(EntryCategory.class);
        breakdown.put(EntryCategory.INCOME, new LinkedHashMap<>());
        breakdown.put(EntryCategory.EXPENSE, new LinkedHashMap<>());
        breakdown.put(EntryCategory.SAVINGS, new LinkedHashMap<>());

        for (Entry entry : entries) {
            double current = totals.get(entry.getCategory());
            totals.put(entry.getCategory(), current + entry.getAmount().getValue());

            Map<String, Double> categoryBreakdown = breakdown.get(entry.getCategory());
            categoryBreakdown.merge(entry.getLabel().getValue(), entry.getAmount().getValue(), Double::sum);
        }

        double income = totals.get(EntryCategory.INCOME);
        double savings = totals.get(EntryCategory.SAVINGS);
        int rate = income > 0 ? (int) Math.round((savings / income) * 100) : 0;
        double grandTotal = income + totals.get(EntryCategory.EXPENSE) + savings;

        List<BreakdownItem> incomeBreakdown = toSortedList(breakdown.get(EntryCategory.INCOME));
        List<BreakdownItem> expenseBreakdown = toSortedList(breakdown.get(EntryCategory.EXPENSE));
        List<BreakdownItem> savingsBreakdown = toSortedList(breakdown.get(EntryCategory.SAVINGS));

        return new SummaryResult(
                new Totals(income, totals.get(EntryCategory.EXPENSE), savings),
                new Breakdown(incomeBreakdown, expenseBreakdown, savingsBreakdown),
                rate,
                grandTotal
        );
    }

    private List<BreakdownItem> toSortedList(Map<String, Double> map) {
        return map.entrySet().stream()
                .map(e -> new BreakdownItem(e.getKey(), e.getValue()))
                .sorted(Comparator.comparingDouble(BreakdownItem::amount).reversed())
                .collect(Collectors.toList());
    }

    public record Totals(double income, double expense, double savings) {}
    public record BreakdownItem(String label, double amount) {}
    public record Breakdown(List<BreakdownItem> income, List<BreakdownItem> expense, List<BreakdownItem> savings) {}
    public record SummaryResult(Totals totals, Breakdown breakdown, int rate, double grandTotal) {}
}
