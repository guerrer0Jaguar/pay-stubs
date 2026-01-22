package com.guerrer0jaguar.paystubs.daosimpl;

import com.guerrer0jaguar.paystubs.entity.Company;
import com.guerrer0jaguar.paystubs.entity.Employee;
import com.guerrer0jaguar.paystubs.entity.PayStub;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class TablesCreator {

    private static final Class<?>[] tablesClasses = { Company.class,
            Employee.class, PayStub.class };

    private final DynamoDbEnhancedClient enhancedClient;

    TablesCreator(DynamoDbEnhancedClient enhancedClient) {
        super();
        this.enhancedClient = enhancedClient;
    }

    void createTables() {
        for (Class<?> tableClass : tablesClasses) {
            createTable(enhancedClient, tableClass);
        }
    }

    private <T> DynamoDbTable<T> createTable(
            final DynamoDbEnhancedClient dbClient,
            Class<T> classType) {

        DynamoDbTable<T> table = dbClient
                .table(classType.getSimpleName(),
                        TableSchema.fromBean(classType));
        System.out.println("creating table..." + classType.getSimpleName());
        table.createTable();

        return table;
    }
}