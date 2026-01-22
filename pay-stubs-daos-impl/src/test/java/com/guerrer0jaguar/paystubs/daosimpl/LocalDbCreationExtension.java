package com.guerrer0jaguar.paystubs.daosimpl;

import java.net.URI;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.dynamodb.services.local.main.ServerRunner;
import software.amazon.dynamodb.services.local.server.DynamoDBProxyServer;

public class LocalDbCreationExtension
        implements BeforeAllCallback, AfterAllCallback {
    
    private static final String SERVER_PORT = "8000";
    private static final String SERVER = "http://localhost:".concat(SERVER_PORT);
    private DynamoDBProxyServer server;
    private DynamoDbClient standarDBclient;
    private DynamoDbEnhancedClient enhancedDBclient;
    private TablesCreator tablesCreationAux;

    public LocalDbCreationExtension() {
    }

    @Override
    public void beforeAll(
            ExtensionContext context) throws Exception {
        System.out.println("Init DB server...");
        
        server = ServerRunner
                .createServerFromCommandLineArgs(
                        new String[] { "-inMemory", "-port", SERVER_PORT });
        server.start();
        standarDBclient = createStandardDBclient();
        enhancedDBclient = createEnhancedDBClient(standarDBclient);
        tablesCreationAux = new TablesCreator(enhancedDBclient);
        tablesCreationAux.createTables();
    }

    @Override
    public void afterAll(
            ExtensionContext context) throws Exception {
        System.out.println("Closing DB server..");
        try {
            this.server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private DynamoDbClient createStandardDBclient() {
        System.out.println("creating db connection....");
        DynamoDbClient stdClient = DynamoDbClient
                .builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create(SERVER))
                .build();
        return stdClient;
    }
    
    DynamoDbEnhancedClient createEnhancedDBClient(
            DynamoDbClient stdClient) {
        System.out.println("creating enhanced db connection....");
        return DynamoDbEnhancedClient
                .builder()
                .dynamoDbClient(stdClient)
                .build();
    }
    
    DynamoDbClient getStandarDBclient() {
        return standarDBclient;
    }
    
    DynamoDbEnhancedClient getEnhDbEnhanceDBclient() {
        return enhancedDBclient;
    }    
}