package com.guerrer0jaguar.paystubs.daosimpl;

import java.util.Optional;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.entity.Employee;

public class EmployeeDao extends BaseDao<Employee> implements Dao<Employee> {

    EmployeeDao() {
        super(Employee.class);       
    }

    @SuppressWarnings("exports")
    @Override
    public Employee save(
            Employee entity) {
        
        generateIdentity(entity);       
        table.putItem(entity);
        
        return entity;
    }

    @Override
    public Optional<Employee> findById(
            String id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Class<Employee> getType() {
        // TODO Auto-generated method stub
        return null;
    }

}
