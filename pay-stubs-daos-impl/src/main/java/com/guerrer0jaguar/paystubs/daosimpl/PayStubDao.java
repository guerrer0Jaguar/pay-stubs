package com.guerrer0jaguar.paystubs.daosimpl;

import java.util.Objects;
import java.util.Optional;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.entity.PayStub;

import software.amazon.awssdk.enhanced.dynamodb.Key;

public class PayStubDao extends BaseDao<PayStub> implements Dao<PayStub> {

    
    public PayStubDao() {
        super(PayStub.class);        
    }

    @SuppressWarnings("exports")
    @Override
    public PayStub save(
            PayStub entity) {

        generateIdentity(entity);
        table.putItem(entity);
        
        return entity;
    }

    @SuppressWarnings("exports")
    @Override
    public Optional<PayStub> findById(
            String id) {
        
        Key key = Key
                .builder()
                .partitionValue(id)
                .build();
        
        PayStub entity = table.getItem(k-> k.key(key));
        
        return Objects.isNull(entity) ?
                Optional.empty() : 
                Optional.of( entity);
    }

    @SuppressWarnings("exports")
    @Override
    public Class<PayStub> getType() {        
        return PayStub.class;
    }
}