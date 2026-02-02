package com.guerrer0jaguar.paystubs.daosimpl;


import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.entity.Employee;

public class EmployeeDao extends BaseDao<Employee> implements Dao<Employee> {

    EmployeeDao() {
        super(Employee.class);       
    }

    @SuppressWarnings("exports")
    @Override
    public Class<Employee> getType() {
        return Employee.class;
    }
}
