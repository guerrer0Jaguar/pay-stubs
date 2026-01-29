package com.guerrer0jaguar.paystubs.daosimpl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.guerrer0jaguar.paystubs.entity.Company;
import com.guerrer0jaguar.paystubs.entity.Employee;
import com.guerrer0jaguar.paystubs.entity.PayStub;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.internal.waiters.ResponseOrException;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

@Slf4j
class TablesCreationTest {
    
    @RegisterExtension
    static LocalDbCreationExtension localDB = new LocalDbCreationExtension();

    @Test
    void test() {
        log.info("running test...");
        printTableAttributes(Company.class);
        printTableAttributes(Employee.class);
        printTableAttributes(PayStub.class);
    }

    private <T>void printTableAttributes(Class<T> type) {
        TableSchema<T> schema = TableSchema.fromBean(type);
        assertFalse(schema.attributeNames().isEmpty());

        String attributes = schema
                .attributeNames()
                .stream()
                .collect(Collectors.joining(","));
        
        log.info("Attributes(local) for {}: {}" , type.getSimpleName(), attributes);      
        
        DynamoDbClient standardClient = DbConnection.getInstance().getStandarDBclient();
        
        try (DynamoDbWaiter waiter = DynamoDbWaiter.builder().client(standardClient).build()) { 
            ResponseOrException<DescribeTableResponse> response = waiter
                    .waitUntilTableExists(builder -> builder.tableName(type.getSimpleName()).build())
                    .matched();
            DescribeTableResponse tableDescription = response.response().orElseThrow(
                    () -> new RuntimeException(type.getSimpleName() +  " table was not created."));
            
            String serverAttr = tableDescription
                    .table()                    
                    .attributeDefinitions()
                    .stream()
                    .map(attr -> attr.attributeName())
                    .collect(Collectors.joining(","));
            
            log.info("Attributes(server) names for {}: {}" , type.getSimpleName(), serverAttr);                              
        }
    }
}