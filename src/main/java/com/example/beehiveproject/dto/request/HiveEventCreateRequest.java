package com.example.beehiveproject.dto.request;

public class HiveEventCreateRequest {
    private Long hiveId;
    private String eventType; // "OLUM" veya "COKUS"
    private String description;

    public Long getHiveId() { return hiveId; }
    public void setHiveId(Long hiveId) { this.hiveId = hiveId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
} 