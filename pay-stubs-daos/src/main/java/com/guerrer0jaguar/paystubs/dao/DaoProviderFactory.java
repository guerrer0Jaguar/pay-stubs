package com.guerrer0jaguar.paystubs.dao;

public interface DaoProviderFactory<T, K> {
    Dao<T,K> createDao();
}