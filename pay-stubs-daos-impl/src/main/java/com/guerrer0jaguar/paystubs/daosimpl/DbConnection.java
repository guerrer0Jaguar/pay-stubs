package com.guerrer0jaguar.paystubs.daosimpl;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

final class DbConnection {
    
    private static DbConnection instance;
    private final DynamoDbClient standarDBclient;
    private final DynamoDbEnhancedClient enhancedDBclient;
    
    private DbConnection() {
        standarDBclient = createStandardDBclient();
        enhancedDBclient = createEnhancedDBClient(standarDBclient);
    }
    
    static final DbConnection getInstance() {
        if ( instance == null) {
            instance = new DbConnection();
        }
        
        return instance;
    }
    
    private DynamoDbClient createStandardDBclient() {
        
        return DynamoDbClient
                .builder()                                
                .build();        
    }
    
    private DynamoDbEnhancedClient createEnhancedDBClient(
            DynamoDbClient stdClient) {
        
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
