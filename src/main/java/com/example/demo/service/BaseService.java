package com.example.demo.service;

import com.example.demo.entity.AbstractBaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<T extends AbstractBaseEntity, ID extends Serializable> {
	T save(T entity);

	List<T> findAll(); // you might want a generic Collection if u prefer

	Optional<T> findById(ID entityId);

	T update(T entity);

	T updateById(T entity, ID entityId);

	void delete(T entity);

	void deleteById(ID entityId);
	// other methods u might need to be generic

}