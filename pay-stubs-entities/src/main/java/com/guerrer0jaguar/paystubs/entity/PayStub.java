package com.guerrer0jaguar.paystubs.entity;

import java.time.Instant;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbFlatten;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Data
@ToString
@EqualsAndHashCode
public class PayStub  {
  
  @Getter(onMethod_=@DynamoDbSortKey)
  private String id;
  @Getter(onMethod_ = @DynamoDbSecondarySortKey(indexNames = "payStubByDate"))
  private Instant creationDate;
  @Getter(onMethod_ = @DynamoDbFlatten)  
  private Employee employee;
  private String urlFile;
}
