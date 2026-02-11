package com.guerrer0jaguar.paystubs.dao;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface Dao<T, K> {
    T save(
            T entity);

    Optional<T> findById(
            K key);

    List<T> findByTimeCreation(
            Instant from,
            Instant until,
            String partitionValue);

    Class<T> getType();
}