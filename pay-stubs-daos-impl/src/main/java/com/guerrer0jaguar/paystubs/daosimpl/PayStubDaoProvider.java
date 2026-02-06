package com.guerrer0jaguar.paystubs.daosimpl;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.dao.DaoProviderFactory;
import com.guerrer0jaguar.paystubs.entity.PayStub;

import software.amazon.awssdk.enhanced.dynamodb.Key;

public class PayStubDaoProvider implements DaoProviderFactory<PayStub, Key> {

    @SuppressWarnings("exports")
    @Override
    public Dao<PayStub, Key> createDao() {       
        return new PayStubDao();
    }
}