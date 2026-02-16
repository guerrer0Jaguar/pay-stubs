package com.guerrer0jaguar.paystubs.rendering;

import com.guerrer0jaguar.paystubs.entity.PayStub;

public interface PayStubRendering {
    
    @SuppressWarnings("exports")
    byte[] generatePDF( PayStub payStub);
}
