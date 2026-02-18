package com.guerrer0jaguar.paystubs.rendering;

import java.io.IOException;
import java.util.Objects;

import com.guerrer0jaguar.paystubs.entity.PayStub;

public interface PayStubRendering {
    
    @SuppressWarnings("exports")
    byte[] generatePayStubRepresentation( PayStub payStub) 
            throws IOException;
    
    @SuppressWarnings("exports")
    default boolean isPayStubValid(PayStub payStub) {
        if ( Objects.isNull(payStub)) {
            return false;
        }
        
        if ( Objects.isNull(payStub.getEmployee())) {
            return false;
        }
        
        if ( Objects.isNull(payStub.getEmployee().getFirstName())) {
            return false;
        }
        
        if ( Objects.isNull(payStub.getEmployee().getLastName())) {
            return false;
        }
        
        return true;
    }
}
