package com.guerrer0jaguar.paystubs.daosimpl;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.guerrer0jaguar.paystubs.entity.PayStub;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

abstract class BaseDao<T extends PayStub> {

    protected final DynamoDbEnhancedClient dynamoDBClient;
    protected final DynamoDbTable<T> table;
    
    BaseDao(Class<T> type) {       
        this.dynamoDBClient = DbConnection.getInstance().getEnhDbEnhanceDBclient();

        table = dynamoDBClient.table(type.getSimpleName(),
                TableSchema.fromBean(type));
    }
    
    public T save(
            T entity) {
        
        generateIdentity(entity);       
        table.putItem(entity);
        
        return entity;
    }
    
    public Optional<T> findById(
            Key key) {
               
       T entity = table.getItem(k -> k.key(key));
       
       return Objects.isNull(entity) ?
               Optional.empty() :
               Optional.of(entity);
    }
 
    void generateIdentity(T entity){
     String id = UUID.randomUUID().toString();
     entity.setId(id);
     entity.setCreationDate(Instant.now());                                               
    }
}