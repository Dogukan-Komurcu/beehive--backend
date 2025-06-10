package com.example.beehiveproject.repository;

import com.example.beehiveproject.entity.HiveEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HiveEventRepository extends JpaRepository<HiveEvent, Long> {
    List<HiveEvent> findAllByUser_Id(String userId);
    List<HiveEvent> findAllByHive_Id(Long hiveId);
} 