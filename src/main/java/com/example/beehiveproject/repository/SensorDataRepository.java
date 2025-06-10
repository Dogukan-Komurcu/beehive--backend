package com.example.beehiveproject.repository;

import com.example.beehiveproject.entity.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    List<SensorData> findBySensor_IdOrderByTimestampDesc(Long sensorId);
} 