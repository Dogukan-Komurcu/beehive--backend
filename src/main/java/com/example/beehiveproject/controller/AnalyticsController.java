package com.example.beehiveproject.controller;

import com.example.beehiveproject.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/timeseries")
    public List<Map<String, Object>> getTimeSeries(@RequestParam Long kovanId, @RequestParam String range) {
        return analyticsService.getTimeSeriesData(kovanId, range);
    }

    @GetMapping("/summary")
    public Map<String, Double> getSummary(@RequestParam Long kovanId, @RequestParam String range) {
        return analyticsService.getSummaryStats(kovanId, range);
    }
} 