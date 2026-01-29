package com.guerrer0jaguar.paystubs.daosimpl;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.entity.BaseEntity;
import com.guerrer0jaguar.paystubs.entity.PayStub;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class PayStubDao implements Dao<PayStub> {

    private final DynamoDbEnhancedClient dynamoDBClient;
    private final DynamoDbTable<PayStub> table;
    
    public PayStubDao() {
        this.dynamoDBClient = DbConnection.getInstance().getEnhDbEnhanceDBclient();
        table = this.dynamoDBClient.table(PayStub.class.getSimpleName(),
                TableSchema.fromBean(PayStub.class));
    }

    @SuppressWarnings("exports")
    @Override
    public PayStub save(
            PayStub entity) {

        String id = UUID.randomUUID().toString();                
        entity.setEntity(new BaseEntity(id, Instant.now()));
        table.putItem(entity);
        return entity;
    }

    @SuppressWarnings("exports")
    @Override
    public Optional<PayStub> findById(
            String id) {
        
        Key key = Key
                .builder()
                .partitionValue(id)
                .build();
        
        PayStub entity = table.getItem(k-> k.key(key));
        
        return Objects.isNull(entity) ?
                Optional.empty() : 
                Optional.of( entity);
    }

    @SuppressWarnings("exports")
    @Override
    public Class<PayStub> getType() {        
        return PayStub.class;
    }
}