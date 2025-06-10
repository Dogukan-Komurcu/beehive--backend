package com.example.beehiveproject.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class HiveSensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hive_id")
    @JsonIgnore
    private Hive hive;

    private Double temperature;
    private Double humidity;
    private Double battery;
    private Boolean motion;
    private LocalDateTime timestamp;
    private Double activity;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Hive getHive() { return hive; }
    public void setHive(Hive hive) { this.hive = hive; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }
    public Double getBattery() { return battery; }
    public void setBattery(Double battery) { this.battery = battery; }
    public Boolean getMotion() { return motion; }
    public void setMotion(Boolean motion) { this.motion = motion; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public Double getActivity() { return activity; }
    public void setActivity(Double activity) { this.activity = activity; }
} 