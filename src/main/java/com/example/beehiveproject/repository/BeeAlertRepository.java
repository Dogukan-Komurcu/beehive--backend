package com.example.beehiveproject.repository;

import com.example.beehiveproject.entity.BeeAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BeeAlertRepository extends JpaRepository<BeeAlert, Long> {
    List<BeeAlert> findByYear(int year);
    List<BeeAlert> findByYearAndMonth(int year, int month);
} 