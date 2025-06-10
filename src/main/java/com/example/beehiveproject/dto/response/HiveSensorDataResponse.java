package com.example.beehiveproject.dto.response;

import java.time.LocalDateTime;

public class HiveSensorDataResponse {
    private Long id;
    private Double temperature;
    private Double humidity;
    private Double battery;
    private Double activity;
    private Boolean motion;
    private LocalDateTime timestamp;
    private Long hiveId;

    // Getter ve Setterlar
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }
    public Double getBattery() { return battery; }
    public void setBattery(Double battery) { this.battery = battery; }
    public Double getActivity() { return activity; }
    public void setActivity(Double activity) { this.activity = activity; }
    public Boolean getMotion() { return motion; }
    public void setMotion(Boolean motion) { this.motion = motion; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public Long getHiveId() { return hiveId; }
    public void setHiveId(Long hiveId) { this.hiveId = hiveId; }
}