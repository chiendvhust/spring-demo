package com.example.demo.repository;

import com.example.demo.entity.AbstractBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T extends AbstractBaseEntity, ID extends Serializable>
		extends JpaRepository<T, ID> {

}