package com.guerrer0jaguar.paystubs.daosimpl;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.dao.DaoProviderFactory;

public class PayStubDaoProvider implements DaoProviderFactory {

    @Override
    public Dao<?> createDao() {      
        return new PayStubDao();
    }
}