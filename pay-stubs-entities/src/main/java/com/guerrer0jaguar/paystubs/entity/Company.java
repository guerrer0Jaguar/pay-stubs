package com.guerrer0jaguar.paystubs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Company  extends BaseEntity {

    @Getter(onMethod_=@DynamoDbSortKey)
    private String taxId;// RFC
    private String name;
}