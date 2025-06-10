package com.example.beehiveproject.dto.request;

import java.util.List;

public class MergeHivesRequest {
    private List<Long> hiveIds;
    private String newHiveName;
    private Long targetColonyId;
    private String aciklama;

    public List<Long> getHiveIds() { return hiveIds; }
    public void setHiveIds(List<Long> hiveIds) { this.hiveIds = hiveIds; }
    public String getNewHiveName() { return newHiveName; }
    public void setNewHiveName(String newHiveName) { this.newHiveName = newHiveName; }
    public Long getTargetColonyId() { return targetColonyId; }
    public void setTargetColonyId(Long targetColonyId) { this.targetColonyId = targetColonyId; }
    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
} 