package com.guerrer0jaguar.paystubs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
@Data
@ToString
@EqualsAndHashCode
public class Company {    
    private String companyTaxId;// RFC
    private String name;
}