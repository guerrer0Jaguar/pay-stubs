package com.guerrer0jaguar.paystubs.daosimpl;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import software.amazon.dynamodb.services.local.main.ServerRunner;
import software.amazon.dynamodb.services.local.server.DynamoDBProxyServer;

public class LocalDbCreationExtension
        implements BeforeAllCallback, AfterAllCallback {
    
    private DynamoDBProxyServer server;

    public LocalDbCreationExtension() {
        System.setProperty("sqlite4java.library.path", "native-libs");
    }

    @Override
    public void beforeAll(
            ExtensionContext context) throws Exception {
        System.out.println("Init DB server...");
        
        String port = "8000";
        server = ServerRunner.createServerFromCommandLineArgs(
                new String[] { "-inMemory", "-port", port });
        server.start();
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
}