package com.guerrer0jaguar.paystubs.daosimpl;

import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.entity.Company;

public class CompanyDao extends BaseDao<Company> implements Dao<Company> {

    CompanyDao() {
        super(Company.class);       
    }

    @SuppressWarnings("exports")
    @Override
    public Class<Company> getType() {     
        return Company.class;
    }
}
