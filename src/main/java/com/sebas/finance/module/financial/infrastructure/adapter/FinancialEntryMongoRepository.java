package com.sebas.finance.module.financial.infrastructure.adapter;

import com.sebas.finance.module.financial.infrastructure.persistence.FinancialEntryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface FinancialEntryMongoRepository extends MongoRepository<FinancialEntryDocument, String> {
    List<FinancialEntryDocument> findByUserIdAndMonthBetween(String userId, Date start, Date end);
    List<FinancialEntryDocument> findByUserIdAndMonthBetweenOrderByMonthDesc(String userId, Date start, Date end);
    void deleteByUserIdAndId(String userId, String id);
}
