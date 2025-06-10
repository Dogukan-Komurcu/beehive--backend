package com.example.beehiveproject.dto.request;

import java.util.List;

public class MoveHivesRequest {
    private List<Long> hiveIds;
    private Long targetColonyId;

    public List<Long> getHiveIds() { return hiveIds; }
    public void setHiveIds(List<Long> hiveIds) { this.hiveIds = hiveIds; }
    public Long getTargetColonyId() { return targetColonyId; }
    public void setTargetColonyId(Long targetColonyId) { this.targetColonyId = targetColonyId; }
} 