package com.guerrer0jaguar.paystubs.mainapp;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

class PayStubLambdaHandlerTest {

    @Test
    @SetEnvironmentVariable(key = "PDF_BUCKET", value = "pay-stubs-app-pdf-bucket")
    void testHandler() {
        PayStubLambdaHandler lambda = new PayStubLambdaHandler();
        assertNotNull(lambda);
    }
}