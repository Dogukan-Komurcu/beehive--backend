package com.example.beehiveproject.dto.response;

public class HiveAlertResponse {
    public Long id;
    public Long hiveId;
    public String hiveName;
    public String message;
    public String type;
    public String timestamp;
    public boolean isRead;
    public String userId;

    public HiveAlertResponse(Long id, Long hiveId, String hiveName, String message, String type, String timestamp, boolean isRead, String userId) {
        this.id = id;
        this.hiveId = hiveId;
        this.hiveName = hiveName;
        this.message = message;
        this.type = type;
        this.timestamp = timestamp;
        this.isRead = isRead;
        this.userId = userId;
    }

    // Getter ve Setterlar
    // ...
}
