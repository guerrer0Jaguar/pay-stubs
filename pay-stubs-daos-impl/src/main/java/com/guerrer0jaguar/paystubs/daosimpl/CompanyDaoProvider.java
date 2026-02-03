package com.guerrer0jaguar.paystubs.daosimpl;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.dao.DaoProviderFactory;

public class CompanyDaoProvider implements DaoProviderFactory {

    @Override
    public Dao<?> createDao() {       
        return new CompanyDao();
    }
}