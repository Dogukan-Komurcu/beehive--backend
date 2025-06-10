package com.example.beehiveproject.dto.request;

public class SplitHiveRequest {
    private String newHiveName;
    private Long targetColonyId;

    public String getNewHiveName() { return newHiveName; }
    public void setNewHiveName(String newHiveName) { this.newHiveName = newHiveName; }
    public Long getTargetColonyId() { return targetColonyId; }
    public void setTargetColonyId(Long targetColonyId) { this.targetColonyId = targetColonyId; }
} 