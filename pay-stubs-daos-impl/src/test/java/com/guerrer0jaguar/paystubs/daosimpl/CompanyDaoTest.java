package com.guerrer0jaguar.paystubs.daosimpl;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.dao.DaoProviderFactory;
import com.guerrer0jaguar.paystubs.entity.Company;

class CompanyDaoTest {
    @RegisterExtension
    static LocalDbCreationExtension localDB = new LocalDbCreationExtension();

    @Test
    void testSave() {
        Dao<Company> dao = createDao();
        Company company = new Company();
        company.setTaxId("CVF010907WDS");
        company.setName("Monday Inc.");
        Company companyDB = dao.save(company);
        assertNotNull(companyDB.getId());
        assertNotNull(companyDB.getCreationDate());
    }
    
    @Test
    void testFindById() {
        Dao<Company> dao = createDao();
        Company company = new Company();
        company.setTaxId("STG010907WDS");
        company.setName("Tuesday Inc.");
        Company companyDB = dao.save(company);
        Optional<Company> companyFound = dao.findById(companyDB.getId());
        assertTrue(companyFound.isPresent());
    }

    @SuppressWarnings("unchecked")
    private Dao<Company> createDao() {
        DaoProviderFactory factory = new CompanyDaoProvider();        
        return (Dao<Company>) factory.createDao();
    }

}
