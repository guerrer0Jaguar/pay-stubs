package com.guerrer0jaguar.paystubs.daosimpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.dao.DaoProviderFactory;
import com.guerrer0jaguar.paystubs.entity.Employee;
import com.guerrer0jaguar.paystubs.entity.PayStub;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@Slf4j
class PayStubDaoTest {
    @RegisterExtension
    static LocalDbCreationExtension localDB = new LocalDbCreationExtension();

    @Test
    void testSave() {

        Dao<PayStub,Key> dao = createDAO();
        PayStub payStub = createPayStubEntity("https://test.com/file1.pdf");
        payStub =  dao.save(payStub);
        assertNotNull(payStub.getId());
        assertNotNull(payStub.getCreationDate());
    }

    @Test
    void testFindById() {
        Dao<PayStub,Key> dao = createDAO();
        PayStub payStub = createPayStubEntity("https://test.com/file2.pdf");
        payStub = dao.save(payStub);        
        
        Key key = Key
                   .builder()
                   .partitionValue(payStub.getEmployee().getTaxId())
                   .addSortValue(payStub.getId())
                   .build();
        
        Optional<PayStub> payStubFound = dao.findById(key);        
        assertTrue(payStubFound.isPresent());                
    }
    
    @Test
    void testByCreationDate() {
        Dao<PayStub,Key> dao = createDAO();
        List<PayStub> toSave = new ArrayList<>();
        
        PayStub payStub = createPayStubEntity("url1");
        payStub.setCreationDate(Instant.parse("2025-02-06T10:15:30.00Z"));
        toSave.add(payStub);
        
        PayStub payStub2 = createPayStubEntity("url2");
        payStub2.setCreationDate(Instant.parse("2025-02-06T11:15:30.00Z"));
        toSave.add(payStub2);
        
        PayStub payStub3 = createPayStubEntity("url3");
        payStub3.setCreationDate(Instant.parse("2025-02-06T23:59:59.59Z"));
        toSave.add(payStub3);
        
        PayStub payStub4 = createPayStubEntity("url4");
        toSave.add(payStub4);
        
        toSave.stream().forEach(dao::save);
        
        List<PayStub> itemsFound = dao
                .findByTimeCreation(
                        fromShortDateToInstant("2025-02-06"), 
                        fromShortDateToInstant("2025-02-07"), 
                        payStub.getEmployee().getTaxId());
        assertFalse(itemsFound.isEmpty());
        assertTrue(itemsFound.size() == 3);
                
        Instant tomorrow = Instant.now().plus(1, ChronoUnit.DAYS);
        List<PayStub> otherItems = dao.findByTimeCreation(
                Instant.now(), 
                tomorrow,
                payStub.getEmployee().getTaxId());
        assertFalse(otherItems.isEmpty());
        assertTrue(otherItems.size() == 1);
        log.info("Last element created at {}", otherItems.getFirst().getCreationDate());
    }

    private Instant fromShortDateToInstant(String date) {
        LocalDate ld = LocalDate.parse(date);        
        return ld.atStartOfDay(ZoneOffset.UTC).toInstant();
    }
    
    private Dao<PayStub, Key> createDAO() {
        DaoProviderFactory<PayStub,Key> factory = new PayStubDaoProvider();
        Dao<PayStub,Key> dao = factory.createDao();        
        assertNotNull(dao);
        
        return dao;
    }
    
    private PayStub createPayStubEntity(String urlFile) {
        PayStub payStub = new PayStub();       
        Employee employee = new Employee("AWS780921QAW");
        payStub.setEmployee(employee);
        payStub.setUrlFile(urlFile);
        
        return payStub;
    }
}
