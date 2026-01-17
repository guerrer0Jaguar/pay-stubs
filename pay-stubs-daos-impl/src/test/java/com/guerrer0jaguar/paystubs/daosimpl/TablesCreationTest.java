package com.guerrer0jaguar.paystubs.daosimpl;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.guerrer0jaguar.paystubs.entity.Company;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.internal.waiters.ResponseOrException;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

class TablesCreationTest {

    @RegisterExtension
    static LocalDbCreationExtension localDB = new LocalDbCreationExtension();

    @Test
    void test() {
        System.out.println("running test...");
        DynamoDbClient stdClient = createStandardDBclient();
        System.out.println("client db connection established");
        DynamoDbEnhancedClient dbClient = createClientDBconnection(stdClient);
        System.out.println("client db enhanced established");
        // just a pointer to the table, doesn't exist still!!
        final DynamoDbTable<Company> companyTable = dbClient.table("Company",
                TableSchema.fromBean(Company.class));

        // creating de table:
        System.out.println("creating table...");
        companyTable.createTable(builder -> builder.provisionedThroughput(
                b -> b.readCapacityUnits(10L).writeCapacityUnits(10L).build()));
        System.out.println("table created(?)...");
        validateTableCreation(stdClient, "Company");
    }

    private DynamoDbClient createStandardDBclient() {
        System.out.println("creating db connection....");
        DynamoDbClient stdClient = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create("http://localhost:8000"))
                .build();
        return stdClient;
    }
    private DynamoDbEnhancedClient createClientDBconnection(DynamoDbClient stdClient) {
        System.out.println("creating enhanced db connection....");
        return DynamoDbEnhancedClient.builder().dynamoDbClient(stdClient)
                .build();
    }

   

    private void validateTableCreation(
            DynamoDbClient stdClient,
            String string) {
        
        try (DynamoDbWaiter waiter = DynamoDbWaiter.builder()
                .client(stdClient).build()) { 
            ResponseOrException<DescribeTableResponse> response = waiter
                    .waitUntilTableExists(
                            builder -> builder.tableName("Company").build())
                    .matched();
            DescribeTableResponse tableDescription = response.response()
                    .orElseThrow(() -> new RuntimeException(
                            "Customer table was not created."));
            // The actual error can be inspected in response.exception()
            System.out.println("Table description: " + tableDescription);
        }

    }
}
