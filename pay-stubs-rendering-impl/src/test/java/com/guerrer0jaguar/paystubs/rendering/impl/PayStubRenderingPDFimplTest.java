package com.guerrer0jaguar.paystubs.rendering.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.guerrer0jaguar.paystubs.entity.Company;
import com.guerrer0jaguar.paystubs.entity.Employee;
import com.guerrer0jaguar.paystubs.entity.PayStub;
import com.guerrer0jaguar.paystubs.rendering.PayStubRendering;

class PayStubRenderingPDFimplTest {

    @Test
    void generatePayStubRepresentation() throws IOException {
        PayStubRendering render = new PayStubRenderingPDFimpl();
        
        PayStub payStub = new PayStub();
        payStub.setId("9740606F-9A48-4ADB-8T63-9F46BA1TY110");
        payStub.setCreationDate(Instant.now());
        Company company = new Company();
        company.setName("A very important Company");
        Employee employee = new Employee();
        employee.setFirstName("Anne");
        employee.setLastName("Rice");
        employee.setCompany(company);
        payStub.setEmployee(employee);
        payStub.setTotal(new BigDecimal("123456789.12"));
        
        byte[] representation = render.generatePayStubRepresentation(payStub);
        assertTrue(representation.length > 0);
        
        Files.write(Paths.get("target/output.pdf"), 
                representation, 
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE
                );
    }

}