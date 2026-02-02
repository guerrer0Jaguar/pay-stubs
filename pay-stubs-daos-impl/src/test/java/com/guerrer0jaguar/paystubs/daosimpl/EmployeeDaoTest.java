package com.guerrer0jaguar.paystubs.daosimpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.dao.DaoProviderFactory;
import com.guerrer0jaguar.paystubs.entity.Employee;

class EmployeeDaoTest {
    @RegisterExtension
    static LocalDbCreationExtension localDB = new LocalDbCreationExtension();

    @Test
    void testSave() {
        
        Dao<Employee> dao = generateDao();
        Employee emp = new Employee();
        emp.setFirstName("Anne");
        emp.setLastName("Smith");
        emp.setTaxId("AAA890923AWZ");
        Employee empDB = dao.save(emp);
        assertNotNull(empDB.getId());
        assertNotNull(empDB.getCreationDate());
    }
    
    @Test 
    void testFindById() {
        Dao<Employee> dao = generateDao();
        Employee emp = new Employee();
        emp.setFirstName("Jane");
        emp.setLastName("Finn");
        emp.setTaxId("AAF9900927AQH");
        Employee empDb = dao.save(emp);        
        Optional<Employee> empFound = dao.findById(empDb.getId());
        assertTrue(empFound.isPresent());
    }
    
    @SuppressWarnings("unchecked")
    private Dao<Employee> generateDao() {
        DaoProviderFactory factory = new EmployeeDaoProvider();
        
        Dao<Employee> dao = (Dao<Employee>) factory.createDao();
        return dao;
    }
}
