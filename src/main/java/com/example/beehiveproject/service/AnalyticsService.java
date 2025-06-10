package com.example.beehiveproject.service;

import com.example.beehiveproject.entity.HiveSensorData;
import com.example.beehiveproject.repository.HiveSensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {
    @Autowired
    private HiveSensorDataRepository hiveSensorDataRepository;

    public List<Map<String, Object>> getTimeSeriesData(Long hiveId, String range) {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start;
        ChronoUnit unit;
        switch (range) {
            case "24h":
                start = end.minusHours(24);
                unit = ChronoUnit.HOURS;
                break;
            case "7d":
                start = end.minusDays(7);
                unit = ChronoUnit.DAYS;
                break;
            case "30d":
                start = end.minusDays(30);
                unit = ChronoUnit.DAYS;
                break;
            default:
                throw new IllegalArgumentException("Invalid range");
        }
        List<HiveSensorData> data = hiveSensorDataRepository.findByHive_IdAndTimestampBetween(hiveId, start, end);
        Map<String, List<HiveSensorData>> grouped;
        if (unit == ChronoUnit.HOURS) {
            grouped = data.stream().collect(Collectors.groupingBy(d -> d.getTimestamp().getHour() + ":00"));
        } else {
            grouped = data.stream().collect(Collectors.groupingBy(d -> d.getTimestamp().getDayOfWeek().toString().substring(0,3)));
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for (String key : grouped.keySet()) {
            List<HiveSensorData> group = grouped.get(key);
            Map<String, Object> entry = new HashMap<>();
            entry.put("name", key);
            entry.put("sicaklik", group.stream().mapToDouble(h -> Optional.ofNullable(h.getTemperature()).orElse(0.0)).average().orElse(0));
            entry.put("nem", group.stream().mapToDouble(h -> Optional.ofNullable(h.getHumidity()).orElse(0.0)).average().orElse(0));
            entry.put("aktivite", group.stream().mapToDouble(h -> Optional.ofNullable(h.getActivity()).orElse(0.0)).average().orElse(0));
            entry.put("batarya", group.stream().mapToDouble(h -> Optional.ofNullable(h.getBattery()).orElse(0.0)).average().orElse(0));
            result.add(entry);
        }
        return result;
    }

    public Map<String, Double> getSummaryStats(Long hiveId, String range) {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start;
        switch (range) {
            case "24h":
                start = end.minusHours(24);
                break;
            case "7d":
                start = end.minusDays(7);
                break;
            case "30d":
                start = end.minusDays(30);
                break;
            default:
                throw new IllegalArgumentException("Invalid range");
        }
        List<HiveSensorData> data = hiveSensorDataRepository.findByHive_IdAndTimestampBetween(hiveId, start, end);
        Map<String, Double> summary = new HashMap<>();
        summary.put("sicaklik", data.stream().mapToDouble(h -> Optional.ofNullable(h.getTemperature()).orElse(0.0)).average().orElse(0));
        summary.put("nem", data.stream().mapToDouble(h -> Optional.ofNullable(h.getHumidity()).orElse(0.0)).average().orElse(0));
        summary.put("aktivite", data.stream().mapToDouble(h -> Optional.ofNullable(h.getActivity()).orElse(0.0)).average().orElse(0));
        summary.put("batarya", data.stream().mapToDouble(h -> Optional.ofNullable(h.getBattery()).orElse(0.0)).average().orElse(0));
        return summary;
    }
} 