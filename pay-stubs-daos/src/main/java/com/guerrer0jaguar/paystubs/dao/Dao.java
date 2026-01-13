package com.guerrer0jaguar.paystubs.dao;

import java.util.Optional;

public interface Dao<T> {
	T save(T entity);
	Optional<T> findById(long id);
}