package com.guerrer0jaguar.paystubs.daosimpl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.entity.BaseEntity;
import com.guerrer0jaguar.paystubs.entity.PayStub;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class PayStubDao implements Dao<PayStub> {

    private final DynamoDbEnhancedClient dynamoDBClient;
    private final DynamoDbTable<PayStub> table;
    
    public PayStubDao() {
        this.dynamoDBClient = null;
        this.table = null;
    }

    public PayStubDao(DynamoDbEnhancedClient dynamoClient) {
        dynamoDBClient = dynamoClient;
        table = this.dynamoDBClient.table("pay_stubs",
                TableSchema.fromBean(PayStub.class));
    }

    @Override
    public PayStub save(
            PayStub entity) {

        String id = UUID.randomUUID().toString();                
        entity.setEntity(new BaseEntity(id, Instant.now()));
        table.putItem(entity);
        return entity;
    }

    @Override
    public Optional<PayStub> findById(
            String id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }
}