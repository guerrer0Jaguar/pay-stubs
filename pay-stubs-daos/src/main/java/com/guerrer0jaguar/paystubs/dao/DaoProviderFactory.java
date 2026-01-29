package com.guerrer0jaguar.paystubs.dao;

public interface DaoProviderFactory {
    Dao<?> createDao();
}