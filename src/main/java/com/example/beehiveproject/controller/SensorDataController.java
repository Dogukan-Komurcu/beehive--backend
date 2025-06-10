package com.example.beehiveproject.controller;

import com.example.beehiveproject.entity.SensorData;
import com.example.beehiveproject.repository.SensorDataRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sensor-data")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class SensorDataController {
    private final SensorDataRepository sensorDataRepository;

    public SensorDataController(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    @GetMapping("/{sensorId}")
    public List<SensorData> getSensorData(@PathVariable Long sensorId) {
        return sensorDataRepository.findBySensor_IdOrderByTimestampDesc(sensorId);
    }

    @PostMapping
    public SensorData addSensorData(@RequestBody SensorData data) {
        return sensorDataRepository.save(data);
    }

    @DeleteMapping("/{id}")
    public void deleteSensorData(@PathVariable Long id) {
        sensorDataRepository.deleteById(id);
    }
} 