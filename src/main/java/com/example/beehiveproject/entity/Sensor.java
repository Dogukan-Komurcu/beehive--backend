package com.example.beehiveproject.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @ManyToOne
    @JoinColumn(name = "hive_id")
    private Hive hive;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private java.util.List<SensorData> sensorDataList;

    // Getter ve Setter'lar
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Hive getHive() { return hive; }
    public void setHive(Hive hive) { this.hive = hive; }
    public java.util.List<SensorData> getSensorDataList() { return sensorDataList; }
    public void setSensorDataList(java.util.List<SensorData> sensorDataList) { this.sensorDataList = sensorDataList; }
} 