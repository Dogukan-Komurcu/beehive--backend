package com.example.beehiveproject.dto.request;

import java.util.List;

public class CreateDiseaseRecordRequest {
    private String diseaseName;
    private String description;
    private Long colonyId;
    private List<Long> hiveIds;

    public String getDiseaseName() { return diseaseName; }
    public void setDiseaseName(String diseaseName) { this.diseaseName = diseaseName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getColonyId() { return colonyId; }
    public void setColonyId(Long colonyId) { this.colonyId = colonyId; }
    public List<Long> getHiveIds() { return hiveIds; }
    public void setHiveIds(List<Long> hiveIds) { this.hiveIds = hiveIds; }
} 