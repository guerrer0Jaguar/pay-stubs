package com.guerrer0jaguar.paystubs.dao;

import java.util.Optional;

public interface Dao<T,K> {
    T save(T entity);
    Optional<T> findById(
           K key);
    Class<T> getType();
}