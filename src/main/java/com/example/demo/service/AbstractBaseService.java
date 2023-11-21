package com.example.demo.service;

import com.example.demo.entity.AbstractBaseEntity;
import com.example.demo.repository.BaseRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractBaseService<T extends AbstractBaseEntity, ID extends Serializable> implements BaseService<T, ID> {
	private final BaseRepository<T, ID> baseRepository;

	protected AbstractBaseService(BaseRepository<T, ID> baseRepository) {
		this.baseRepository = baseRepository;
	}

	public T save(T entity) {
		return baseRepository.save(entity);
	}

	public List<T> findAll() {
		return baseRepository.findAll();
	}

	public Optional<T> findById(ID entityId) {
		return baseRepository.findById(entityId);
	}

	public T update(T entity) {
		return baseRepository.save(entity);
	}

	public T updateById(T entity, ID entityId) {
		Optional<T> optional = baseRepository.findById(entityId);
		if (optional.isPresent()) {
			return baseRepository.save(entity);
		} else {
			return null;
		}
	}

	public void delete(T entity) {
		baseRepository.delete(entity);
	}

	public void deleteById(ID entityId) {
		baseRepository.deleteById(entityId);
	}
}