package com.sebas.finance.module.financial.infrastructure.controller;

import com.sebas.finance.module.financial.application.service.FinancialEntryService;
import com.sebas.finance.module.financial.application.usecase.GetSummaryUseCase;
import com.sebas.finance.module.financial.domain.exception.EntryNotFoundException;
import com.sebas.finance.module.financial.domain.exception.FutureMonthException;
import com.sebas.finance.module.financial.domain.exception.InvalidEntryDataException;
import com.sebas.finance.module.financial.domain.exception.InvalidMonthException;
import com.sebas.finance.module.financial.domain.model.*;
import com.sebas.finance.module.financial.infrastructure.controller.dto.EntryDeleteRequest;
import com.sebas.finance.module.financial.infrastructure.controller.dto.EntryRequest;
import com.sebas.finance.module.financial.infrastructure.controller.dto.EntryResponse;
import com.sebas.finance.module.financial.infrastructure.controller.dto.SummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/entries")
@RequiredArgsConstructor
public class FinancialEntryController {

    private final FinancialEntryService financialEntryService;

    private static final Pattern MONTH_PATTERN = Pattern.compile("^\\d{4}-\\d{2}$");

    @GetMapping
    public ResponseEntity<?> getEntries(
            @RequestParam(required = false, defaultValue = "") String month,
            Principal principal) {

        String userId = principal.getName();
        String normalized = normalizeMonth(month);
        if (normalized == null) {
            return badRequest("Mes invalido.");
        }

        YearMonth yearMonth = parseYearMonth(normalized);
        List<Entry> entries = financialEntryService.findByMonth(userId, new EntryMonth(yearMonth));

        List<EntryResponse> response = entries.stream()
                .map(this::toEntryResponse)
                .toList();

        return ResponseEntity.ok(new LinkedHashMap<>(Map.of("entries", response)));
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getSummary(
            @RequestParam(required = false, defaultValue = "") String month,
            @RequestParam(required = false, defaultValue = "monthly") String range,
            Principal principal) {

        String userId = principal.getName();
        String normalized = normalizeMonth(month);
        if (normalized == null) {
            return badRequest("Mes invalido.");
        }

        YearMonth yearMonth = parseYearMonth(normalized);
        boolean annual = "annual".equals(range);

        GetSummaryUseCase.SummaryResult result = financialEntryService.getSummary(userId, yearMonth, annual);

        SummaryResponse response = toSummaryResponse(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody EntryRequest body, Principal principal) {
        String userId = principal.getName();

        String month = String.valueOf(body.getMonth() != null ? body.getMonth() : "");
        String category = String.valueOf(body.getCategory() != null ? body.getCategory() : "");
        String label = String.valueOf(body.getLabel() != null ? body.getLabel() : "").trim();
        double amount = body.getAmount();

        String normalized = normalizeMonth(month);
        if (normalized == null) {
            return badRequest("Mes invalido.");
        }

        YearMonth yearMonth = parseYearMonth(normalized);
        if (yearMonth.isAfter(YearMonth.now())) {
            return badRequest("No puedes registrar meses futuros.");
        }

        EntryCategory entryCategory;
        try {
            entryCategory = EntryCategory.fromString(category);
        } catch (IllegalArgumentException e) {
            return badRequest("Categoria invalida.");
        }

        EntryLabel entryLabel;
        try {
            entryLabel = new EntryLabel(label);
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        }

        EntryAmount entryAmount;
        try {
            entryAmount = new EntryAmount(amount);
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        }

        Entry entry = new Entry(
                null,
                userId,
                new EntryMonth(yearMonth),
                entryCategory,
                entryLabel,
                entryAmount
        );

        financialEntryService.save(entry);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LinkedHashMap<>(Map.of("ok", true)));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEntry(@RequestBody EntryDeleteRequest body, Principal principal) {
        String userId = principal.getName();
        String id = String.valueOf(body.getId() != null ? body.getId() : "");

        if (id.isBlank()) {
            return badRequest("Id invalido.");
        }

        financialEntryService.delete(userId, new EntryId(id));
        return ResponseEntity.ok(new LinkedHashMap<>(Map.of("ok", true)));
    }

    private String normalizeMonth(String value) {
        if (value == null || !MONTH_PATTERN.matcher(value).matches()) {
            return null;
        }
        return value + "-01";
    }

    private YearMonth parseYearMonth(String normalized) {
        try {
            return YearMonth.parse(normalized.substring(0, 7), DateTimeFormatter.ofPattern("yyyy-MM"));
        } catch (DateTimeParseException e) {
            throw new InvalidMonthException("Mes invalido.");
        }
    }

    private ResponseEntity<Map<String, String>> badRequest(String message) {
        return ResponseEntity.badRequest().body(Map.of("message", message));
    }

    private EntryResponse toEntryResponse(Entry entry) {
        EntryResponse response = new EntryResponse();
        response.setId(entry.getEntryId().getValue());
        response.setMonth(entry.getMonth().getValue().toString());
        response.setCategory(entry.getCategory().toLowerCase());
        response.setLabel(entry.getLabel().getValue());
        response.setAmount(entry.getAmount().getValue());
        return response;
    }

    private SummaryResponse toSummaryResponse(GetSummaryUseCase.SummaryResult result) {
        SummaryResponse response = new SummaryResponse();

        SummaryResponse.Totals totals = new SummaryResponse.Totals();
        totals.setIncome(result.totals().income());
        totals.setExpense(result.totals().expense());
        totals.setSavings(result.totals().savings());
        response.setTotals(totals);

        SummaryResponse.Breakdown breakdown = new SummaryResponse.Breakdown();
        breakdown.setIncome(toBreakdownItems(result.breakdown().income()));
        breakdown.setExpense(toBreakdownItems(result.breakdown().expense()));
        breakdown.setSavings(toBreakdownItems(result.breakdown().savings()));
        response.setBreakdown(breakdown);

        response.setRate(result.rate());
        response.setGrandTotal(result.grandTotal());

        return response;
    }

    private List<SummaryResponse.BreakdownItem> toBreakdownItems(List<GetSummaryUseCase.BreakdownItem> items) {
        return items.stream().map(item -> {
            SummaryResponse.BreakdownItem bi = new SummaryResponse.BreakdownItem();
            bi.setLabel(item.label());
            bi.setAmount(item.amount());
            return bi;
        }).toList();
    }
}
