package com.guerrer0jaguar.paystubs.daosimpl;

import java.util.ArrayList;
import java.util.List;

import com.guerrer0jaguar.paystubs.entity.PayStub;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.internal.waiters.ResponseOrException;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse;
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

@Slf4j
public class TablesCreator {

    private static final Class<?>[] tablesClasses = { PayStub.class };

    private final DynamoDbEnhancedClient enhancedClient;
    final DynamoDbClient standardClient = DbConnection.getInstance().getStandarDBclient();

    TablesCreator(DynamoDbEnhancedClient enhancedClient) {
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
        List<String> tablesBeforeEvenStarting = 
                listTables(DbConnection.getInstance().getStandarDBclient());
        if ( !tablesBeforeEvenStarting.isEmpty()) {
            cleanDatabase(null, tablesBeforeEvenStarting);
        }
        log.info("creating table..." + classType.getSimpleName());
        table.createTable();
                
        try (DynamoDbWaiter waiter = DynamoDbWaiter.builder().client(standardClient).build()) { 
            ResponseOrException<DescribeTableResponse> response = waiter
                    .waitUntilTableExists(builder -> builder.tableName(classType.getSimpleName()).build())
                    .matched();
            DescribeTableResponse tableDescription = response.response().orElseThrow(
                    () -> new RuntimeException("table" + classType.getSimpleName() + "was not created."));

            log.info("{} table was created.", tableDescription.table().tableName());
        }

        return table;
    }

    List<String> listTables(
            DynamoDbClient ddb) {
        List<String> allTables = new ArrayList<>();

        boolean moreTables = true;
        String lastName = null;
        log.info("Listing tables....");
        while (moreTables) {

            ListTablesResponse response = null;
            if (lastName == null) {
                ListTablesRequest request = ListTablesRequest.builder().build();

                response = ddb.listTables(request);
            } else {
                ListTablesRequest request = ListTablesRequest
                        .builder()
                        .exclusiveStartTableName(lastName)
                        .build();
                response = ddb.listTables(request);
            }

            List<String> tableNames = response.tableNames();
            allTables.addAll(tableNames);
            if (tableNames.size() > 0) {
                for (String curName : tableNames) {
                    log.info("Table: {}", curName);
                }
            } else {
                log.info("No tables found!");
            }

            lastName = response.lastEvaluatedTableName();
            if (lastName == null) {
                moreTables = false;
            }

        }
        log.info("Listing tables Done!");

        return allTables;
    }

    void cleanDatabase(DynamoDbClient ddb) {
        List<String> tableNames = listTables(ddb);
        cleanDatabase(ddb, tableNames);
    }
    
    private void cleanDatabase(
            DynamoDbClient ddb,
            List<String> tableNames) {

        for (String curName : tableNames) {
            DeleteTableRequest dtR = DeleteTableRequest
                    .builder()
                    .tableName(curName)
                    .build();
            ddb.deleteTable(dtR);
        }
    }
}