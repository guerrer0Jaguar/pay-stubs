package com.guerrer0jaguar.paystubs.daosimpl;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.entity.PayStub;

public class PayStubDao extends BaseDao<PayStub> implements Dao<PayStub> {

    
    public PayStubDao() {
        super(PayStub.class);        
    }

    @SuppressWarnings("exports")
    @Override
    public Class<PayStub> getType() {        
        return PayStub.class;
    }
}