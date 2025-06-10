package com.example.beehiveproject.repository;

import com.example.beehiveproject.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
} 