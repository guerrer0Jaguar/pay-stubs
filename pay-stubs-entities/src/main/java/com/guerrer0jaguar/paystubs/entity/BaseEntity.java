package com.guerrer0jaguar.paystubs.entity;

import java.time.Instant;

import lombok.Data;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDbBean
public abstract class BaseEntity {
    
    @Getter(onMethod_= @DynamoDbPartitionKey)
    private String id;
    private Instant creationDate;

    public BaseEntity() {}
    
    public BaseEntity(String id, Instant creationDate) {
        this.id = id;
        this.creationDate = creationDate;
    }
}