package com.guerrer0jaguar.paystubs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbFlatten;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Data
@ToString
@EqualsAndHashCode
public class Employee {
    
    private static final String SPACE = " ";
    @Getter(onMethod_ = @DynamoDbPartitionKey)
    private String taxId;// RFC
    private String firstName;
    private String lastName;
    @Getter(onMethod_=@DynamoDbFlatten)
    private Company company;
    
    public Employee() {}
    
    public Employee(String taxId) {
        this.taxId = taxId;
    }

    public String getFullName() {
        StringBuilder sb = new StringBuilder(firstName);
        sb.append(SPACE);
        sb.append(lastName);
        
        return sb.toString();
    }    
}