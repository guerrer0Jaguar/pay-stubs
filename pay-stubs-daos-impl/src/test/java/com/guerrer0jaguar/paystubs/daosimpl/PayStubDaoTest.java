package com.guerrer0jaguar.paystubs.daosimpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.dao.DaoProviderFactory;
import com.guerrer0jaguar.paystubs.entity.Employee;
import com.guerrer0jaguar.paystubs.entity.PayStub;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class PayStubDaoTest {
    @RegisterExtension
    static LocalDbCreationExtension localDB = new LocalDbCreationExtension();

    @Test
    void testSave() {

        Dao<PayStub> dao = createDAO();
        PayStub payStub = createPayStubEntity("https://test.com/file1.pdf");
        payStub =  dao.save(payStub);
        assertNotNull(payStub.getId());
        assertNotNull(payStub.getCreationDate());
    }

    @Test
    void testFindById() {
        Dao<PayStub> dao = createDAO();
        PayStub payStub = createPayStubEntity("https://test.com/file2.pdf");
        payStub = dao.save(payStub);        
        Optional<PayStub> payStubFound = dao.findById(payStub.getId());        
        assertTrue(payStubFound.isPresent());                
    }
    
    @SuppressWarnings("unchecked")
    private Dao<PayStub> createDAO() {
        DaoProviderFactory factory = new PayStubDaoProvider();
        Dao<PayStub> dao = (Dao<PayStub>) factory.createDao();        
        assertNotNull(dao);
        
        return dao;
    }
    
    private PayStub createPayStubEntity(String urlFile) {
        PayStub payStub = new PayStub();       
        payStub.setEmployee(new Employee());
        payStub.setUrlFile(urlFile);
        
        return payStub;
    }

}
