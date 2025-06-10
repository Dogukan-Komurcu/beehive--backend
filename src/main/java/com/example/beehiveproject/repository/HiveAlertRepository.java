package com.example.beehiveproject.repository;

import com.example.beehiveproject.entity.HiveAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HiveAlertRepository extends JpaRepository<HiveAlert, Long> {
    List<HiveAlert> findByUser_Id(String userId);
    List<HiveAlert> findByHive_Id(Long hiveId);
    List<HiveAlert> findByIsReadFalseAndUser_Id(String userId);
    List<HiveAlert> findByClosedFalseAndUser_Id(String userId);
} 