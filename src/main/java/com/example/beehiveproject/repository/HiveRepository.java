package com.example.beehiveproject.repository;

import com.example.beehiveproject.entity.Hive;
import com.example.beehiveproject.dto.response.YearlyProductionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HiveRepository extends JpaRepository<Hive, Long> {

    long countByIsInfectedTrue();

    long countByNeedsMaintenanceTrue();

    @Query("SELECT SUM(h.yearlyHoneyProductionKg) FROM Hive h")
    Double sumTotalHoneyProduction();

    @Query("SELECT new com.example.beehiveproject.dto.response.YearlyProductionDTO(YEAR(h.lastUpdated), SUM(h.yearlyHoneyProductionKg)) " +
            "FROM Hive h GROUP BY YEAR(h.lastUpdated) ORDER BY YEAR(h.lastUpdated)")
    List<YearlyProductionDTO> getYearlyProduction();

    @Query("SELECT h FROM Hive h WHERE h.konumLat BETWEEN :minLat AND :maxLat AND h.konumLng BETWEEN :minLng AND :maxLng")
    List<Hive> findByLocationBox(
        @Param("minLat") Double minLat,
        @Param("maxLat") Double maxLat,
        @Param("minLng") Double minLng,
        @Param("maxLng") Double maxLng
    );

    List<Hive> findByUser_Id(String userId);

    List<Hive> findAllByParentHiveId(Long parentHiveId);

    List<Hive> findAllByColonyKoloniId(Long koloniId);

    List<Hive> findAllByColonyKoloniIdAndUser_Id(Long koloniId, String userId);

    long countByDurum(String durum);
}