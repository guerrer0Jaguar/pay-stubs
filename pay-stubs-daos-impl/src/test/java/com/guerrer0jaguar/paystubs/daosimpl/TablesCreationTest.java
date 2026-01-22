package com.guerrer0jaguar.paystubs.daosimpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.guerrer0jaguar.paystubs.entity.Company;
import com.guerrer0jaguar.paystubs.entity.Employee;
import com.guerrer0jaguar.paystubs.entity.PayStub;

import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

class TablesCreationTest {

    @RegisterExtension
    static LocalDbCreationExtension localDB = new LocalDbCreationExtension();

    @Test
    void test() {
        System.out.println("running test...");
        printTableAttributes(Company.class);
        printTableAttributes(Employee.class);
        printTableAttributes(PayStub.class);
    }

    private <T>void printTableAttributes(Class<T> type) {
        TableSchema<T> schema = TableSchema.fromBean(type);
        System.out.println("Attributes names for " + type.getSimpleName());
        schema.attributeNames().forEach(System.out::println);
    }
}