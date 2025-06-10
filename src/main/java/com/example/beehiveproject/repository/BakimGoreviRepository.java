package com.example.beehiveproject.repository;

import com.example.beehiveproject.entity.BakimGorevi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BakimGoreviRepository extends JpaRepository<BakimGorevi, Long> {
    List<BakimGorevi> findByTamamlandiFalse();
} 