package com.example.beehiveproject.repository;

import com.example.beehiveproject.entity.Colony;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ColonyRepository extends JpaRepository<Colony, Long> {
    List<Colony> findAllByUser_Id(String userId);
} 