package com.guerrer0jaguar.paystubs.daosimpl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.guerrer0jaguar.paystubs.dao.DaoProviderFactory;

class EmployeeDaoTest {
    @RegisterExtension
    static LocalDbCreationExtension localDB = new LocalDbCreationExtension();

    @Test
    void testSave() {
        DaoProviderFactory factory = new EmployeeDaoProvider();
    }

}
