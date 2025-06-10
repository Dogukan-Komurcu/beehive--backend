package com.example.beehiveproject.dto.response;

import com.example.beehiveproject.entity.DiseaseRecord;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DiseaseRecordResponse {
    private Long id;
    private String diseaseName;
    private String description;
    private LocalDateTime createdAt;
    private Long colonyId;
    private String colonyName;
    private List<HiveSummary> hives;

    public static DiseaseRecordResponse fromEntity(DiseaseRecord record) {
        DiseaseRecordResponse dto = new DiseaseRecordResponse();
        dto.id = record.getId();
        dto.diseaseName = record.getDiseaseName();
        dto.description = record.getDescription();
        dto.createdAt = record.getCreatedAt();
        if (record.getColony() != null) {
            dto.colonyId = record.getColony().getKoloniId();
            dto.colonyName = record.getColony().getAd();
        }
        if (record.getHives() != null) {
            dto.hives = record.getHives().stream().map(HiveSummary::fromEntity).collect(Collectors.toList());
        }
        return dto;
    }

    public static class HiveSummary {
        private Long id;
        private String ad;
        public static HiveSummary fromEntity(com.example.beehiveproject.entity.Hive hive) {
            HiveSummary s = new HiveSummary();
            s.id = hive.getId();
            s.ad = hive.getAd();
            return s;
        }
        public Long getId() { return id; }
        public String getAd() { return ad; }
    }

    public Long getId() { return id; }
    public String getDiseaseName() { return diseaseName; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getColonyId() { return colonyId; }
    public String getColonyName() { return colonyName; }
    public List<HiveSummary> getHives() { return hives; }
} 