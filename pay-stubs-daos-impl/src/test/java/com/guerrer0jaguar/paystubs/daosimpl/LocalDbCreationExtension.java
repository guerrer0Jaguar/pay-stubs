package com.guerrer0jaguar.paystubs.daosimpl;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.dynamodb.services.local.main.ServerRunner;
import software.amazon.dynamodb.services.local.server.DynamoDBProxyServer;

@Slf4j
public class LocalDbCreationExtension
        implements BeforeAllCallback, AfterAllCallback {
    
    private static final String SERVER_PORT = "8000";    
    private DynamoDBProxyServer server;        
    private TablesCreator tablesCreationAux;

    public LocalDbCreationExtension() {
    }

    @SuppressWarnings("exports")
    @Override
    public void beforeAll(
            ExtensionContext context) throws Exception {
            
        server = ServerRunner
                .createServerFromCommandLineArgs(
                        new String[] { "-inMemory", "-port", SERVER_PORT });
        server.start();        
        DynamoDbEnhancedClient enhancedDBclient = DbConnection.getInstance().getEnhDbEnhanceDBclient();
        tablesCreationAux = new TablesCreator(enhancedDBclient);
        tablesCreationAux.createTables();
    }

    @SuppressWarnings("exports")
    @Override
    public void afterAll(
            ExtensionContext context) throws Exception {
        log.info("Closing DB server..");
        try {
            this.server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }    
}