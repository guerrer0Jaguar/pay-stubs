package com.guerrer0jaguar.paystubs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Company  extends BaseEntity {
    
    private String taxId;// RFC
    private String name;
}