package com.guerrer0jaguar.paystubs.daosimpl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.entity.PayStub;

import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

public class PayStubDao extends BaseDao<PayStub> implements Dao<PayStub,Key> {

    public PayStubDao() {
        super(PayStub.class);
    }

    @SuppressWarnings("exports")
    @Override
    public PayStub save(
            PayStub entity) {

        if (entity.getEmployee() == null) {
            throw new IllegalArgumentException(
                    "Data from employee is required!");
        }

        if (entity.getEmployee().getTaxId() == null
                || entity.getEmployee().getTaxId().isBlank()) {
            throw new IllegalArgumentException("Tax Id from employee is required!");             
        }

        return super.save(entity);
    }

    @SuppressWarnings("exports")
    @Override
    public List<PayStub> findByTimeCreation(
            Instant from,
            Instant until,
            String taxId) {
        
        DynamoDbIndex<PayStub> idx = table.index("payStubByDate");
        
        String fromStr = formatInstantToShortDate(from);
        String toStr = formatInstantToShortDate(until.plus(1, ChronoUnit.DAYS));
        
        QueryConditional qry = QueryConditional
                .sortBetween(
                           Key.builder().partitionValue(taxId).sortValue(fromStr).build(),
                           Key.builder().partitionValue(taxId).sortValue(toStr).build() );
          

        QueryEnhancedRequest req = QueryEnhancedRequest.builder()
                .queryConditional(qry)          
                .attributesToProject("id", "employee", "creationDate","urlFile")
                .build();
        
        SdkIterable<Page<PayStub>> pages = idx.query(req);
        
        List<PayStub> allItems = new ArrayList<>();
        
        pages
            .stream()
            .forEach(page -> page
                    .items()
                    .stream()
                    .forEach(allItems::add));        
        
        return allItems;
    }

    private String formatInstantToShortDate(
            Instant toFormat) {
        LocalDate date = toFormat
                .atZone(ZoneOffset.UTC)
                .toLocalDate();
        
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    
    @SuppressWarnings("exports")
    @Override
    public Class<PayStub> getType() {
        return PayStub.class;
    }  
}