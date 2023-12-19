package com.example.demo.repository;

import com.example.demo.entity.AirPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirPortReposiroty extends JpaRepository<AirPort, Long> {
    Optional<AirPort> findByCode(String code);
}
