package com.example.beehiveproject.repository;

import com.example.beehiveproject.entity.DiseaseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DiseaseRecordRepository extends JpaRepository<DiseaseRecord, Long> {
    List<DiseaseRecord> findAllByUser_Id(String userId);
    List<DiseaseRecord> findAllByHives_Id(Long hiveId);
    List<DiseaseRecord> findAllByColony_KoloniId(Long colonyId);
} 