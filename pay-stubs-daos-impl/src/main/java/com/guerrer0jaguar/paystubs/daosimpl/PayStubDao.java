package com.guerrer0jaguar.paystubs.daosimpl;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.entity.PayStub;

import software.amazon.awssdk.enhanced.dynamodb.Key;

public class PayStubDao extends BaseDao<PayStub> implements Dao<PayStub,Key> {

    public PayStubDao() {
        super(PayStub.class);
    }

    @SuppressWarnings("exports")
    @Override
    public PayStub save(
            PayStub entity) {

        if (entity.getEmployee() == null) {
            throw new IllegalArgumentException(
                    "Data from employee is required!");
        }

        if (entity.getEmployee().getTaxId() == null
                || entity.getEmployee().getTaxId().isBlank()) {
            throw new IllegalArgumentException("Tax Id from employee is required!");             
        }

        return super.save(entity);
    }

    @SuppressWarnings("exports")
    @Override
    public Class<PayStub> getType() {
        return PayStub.class;
    }
}