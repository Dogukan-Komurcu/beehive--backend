package com.example.beehiveproject.controller;

import com.example.beehiveproject.entity.Sensor;
import com.example.beehiveproject.repository.SensorRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sensors")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class SensorController {
    private final SensorRepository sensorRepository;

    public SensorController(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @GetMapping
    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    @PostMapping
    public Sensor addSensor(@RequestBody Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @DeleteMapping("/{id}")
    public void deleteSensor(@PathVariable Long id) {
        sensorRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Sensor updateSensor(@PathVariable Long id, @RequestBody Sensor updated) {
        Sensor sensor = sensorRepository.findById(id).orElseThrow();
        sensor.setName(updated.getName());
        sensor.setType(updated.getType());
        sensor.setHive(updated.getHive());
        return sensorRepository.save(sensor);
    }
} 