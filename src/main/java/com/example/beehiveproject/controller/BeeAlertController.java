package com.example.beehiveproject.controller;

import com.example.beehiveproject.entity.BeeAlert;
import com.example.beehiveproject.repository.BeeAlertRepository;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class BeeAlertController {
    private final BeeAlertRepository beeAlertRepository;

    public BeeAlertController(BeeAlertRepository beeAlertRepository) {
        this.beeAlertRepository = beeAlertRepository;
    }

    @GetMapping("/year/{year}")
    public List<BeeAlertResponse> getAlertsByYear(@PathVariable int year) {
        List<BeeAlert> alerts = beeAlertRepository.findByYear(year);
        return alerts.stream().map(BeeAlertResponse::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("/year/{year}/month/{month}")
    public List<BeeAlertResponse> getAlertsByYearAndMonth(@PathVariable int year, @PathVariable int month) {
        List<BeeAlert> alerts = beeAlertRepository.findByYearAndMonth(year, month);
        return alerts.stream().map(BeeAlertResponse::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("/years")
    public List<Integer> getAvailableYears() {
        return beeAlertRepository.findAll().stream().map(BeeAlert::getYear).distinct().sorted().collect(Collectors.toList());
    }

    @PostMapping
    public BeeAlert addAlert(@RequestBody BeeAlert alert) {
        return beeAlertRepository.save(alert);
    }

    @PutMapping("/{id}")
    public BeeAlert updateAlert(@PathVariable Long id, @RequestBody BeeAlert updated) {
        BeeAlert alert = beeAlertRepository.findById(id).orElseThrow();
        alert.setYear(updated.getYear());
        alert.setMonth(updated.getMonth());
        alert.setStartDay(updated.getStartDay());
        alert.setEndDay(updated.getEndDay());
        alert.setTitle(updated.getTitle());
        alert.setIcon(updated.getIcon());
        alert.setType(updated.getType());
        alert.setMessage(updated.getMessage());
        return beeAlertRepository.save(alert);
    }

    @DeleteMapping("/{id}")
    public void deleteAlert(@PathVariable Long id) {
        beeAlertRepository.deleteById(id);
    }

    public static class BeeAlertResponse {
        public Long id;
        public int year;
        public int month;
        public int startDay;
        public int endDay;
        public String title;
        public String icon;
        public String type;
        public String message;
        public long daysLeft;

        public static BeeAlertResponse fromEntity(BeeAlert alert) {
            BeeAlertResponse dto = new BeeAlertResponse();
            dto.id = alert.getId();
            dto.year = alert.getYear();
            dto.month = alert.getMonth();
            dto.startDay = alert.getStartDay();
            dto.endDay = alert.getEndDay();
            dto.title = alert.getTitle();
            dto.icon = alert.getIcon();
            dto.type = alert.getType();
            dto.message = alert.getMessage();
            LocalDate today = LocalDate.now();
            LocalDate eventDate = LocalDate.of(alert.getYear(), alert.getMonth(), alert.getStartDay());
            dto.daysLeft = ChronoUnit.DAYS.between(today, eventDate);
            return dto;
        }
    }
} 