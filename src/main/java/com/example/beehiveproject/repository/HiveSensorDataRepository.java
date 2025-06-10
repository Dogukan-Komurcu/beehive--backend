package com.example.beehiveproject.repository;

import com.example.beehiveproject.entity.HiveSensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HiveSensorDataRepository extends JpaRepository<HiveSensorData, Long> {
    List<HiveSensorData> findByHive_Id(Long hiveId);
    List<HiveSensorData> findByHive_IdAndTimestampBetween(Long hiveId, java.time.LocalDateTime start, java.time.LocalDateTime end);
} 