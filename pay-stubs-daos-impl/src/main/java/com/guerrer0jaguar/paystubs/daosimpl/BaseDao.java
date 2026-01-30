package com.guerrer0jaguar.paystubs.daosimpl;

import java.time.Instant;
import java.util.UUID;

import com.guerrer0jaguar.paystubs.entity.BaseEntity;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

abstract class BaseDao<T extends BaseEntity> {

    protected final DynamoDbEnhancedClient dynamoDBClient;
    protected final DynamoDbTable<T> table;
    
    BaseDao(Class<T> type) {       
        this.dynamoDBClient = DbConnection.getInstance().getEnhDbEnhanceDBclient();

        table = dynamoDBClient.table(type.getSimpleName(),
                TableSchema.fromBean(type));
    }
    
    void generateIdentity(T entity){
     String id = UUID.randomUUID().toString();
     entity.setId(id);
     entity.setCreationDate(Instant.now());                                               
    }
}