package com.example.beehiveproject.dto.response;

import com.example.beehiveproject.entity.HiveEvent;
import java.time.LocalDateTime;

public class HiveEventResponse {
    private Long id;
    private Long hiveId;
    private String hiveName;
    private String eventType;
    private String description;
    private LocalDateTime eventDate;

    public static HiveEventResponse fromEntity(HiveEvent event) {
        HiveEventResponse dto = new HiveEventResponse();
        dto.id = event.getId();
        dto.eventType = event.getEventType();
        dto.description = event.getDescription();
        dto.eventDate = event.getEventDate();
        if (event.getHive() != null) {
            dto.hiveId = event.getHive().getId();
            dto.hiveName = event.getHive().getAd();
        }
        return dto;
    }

    public Long getId() { return id; }
    public Long getHiveId() { return hiveId; }
    public String getHiveName() { return hiveName; }
    public String getEventType() { return eventType; }
    public String getDescription() { return description; }
    public LocalDateTime getEventDate() { return eventDate; }
} 