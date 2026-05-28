package com.sebas.finance.module.financial.infrastructure.adapter;

import com.sebas.finance.module.financial.domain.model.Entry;
import com.sebas.finance.module.financial.domain.model.EntryId;
import com.sebas.finance.module.financial.domain.repository.FinancialEntryRepository;
import com.sebas.finance.module.financial.infrastructure.mapper.FinancialEntryMapper;
import com.sebas.finance.module.financial.infrastructure.persistence.FinancialEntryDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FinancialEntryAdapter implements FinancialEntryRepository {

    private final FinancialEntryMongoRepository mongoRepository;
    private final FinancialEntryMapper mapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public Entry save(Entry entry) {
        var document = mapper.toDocument(entry);
        var savedDocument = mongoRepository.save(document);
        return mapper.toDomain(savedDocument);
    }

    @Override
    public void delete(EntryId id) {
        mongoRepository.deleteById(id.getValue());
    }

    @Override
    public Optional<Entry> findById(EntryId id) {
        return mongoRepository.findById(id.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public List<Entry> findByUserIdAndMonth(String userId, YearMonth month) {
        Date start = Date.from(month.atDay(1).atStartOfDay(ZoneId.of("UTC")).toInstant());
        Date end = Date.from(month.plusMonths(1).atDay(1).atStartOfDay(ZoneId.of("UTC")).toInstant());

        Query query = new Query(Criteria
                .where("userId").is(userId)
                .and("month").gte(start).lt(end))
                .with(Sort.by(Sort.Direction.DESC, "month"));

        return mongoTemplate.find(query, FinancialEntryDocument.class)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Entry> findByUserIdAndYear(String userId, int year) {
        Date start = Date.from(YearMonth.of(year, 1).atDay(1).atStartOfDay(ZoneId.of("UTC")).toInstant());
        Date end = Date.from(YearMonth.of(year + 1, 1).atDay(1).atStartOfDay(ZoneId.of("UTC")).toInstant());

        Query query = new Query(Criteria
                .where("userId").is(userId)
                .and("month").gte(start).lt(end))
                .with(Sort.by(Sort.Direction.DESC, "month"));

        return mongoTemplate.find(query, FinancialEntryDocument.class)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
