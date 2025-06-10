package com.example.beehiveproject.controller;

import com.example.beehiveproject.dto.response.HiveAlertResponse;
import com.example.beehiveproject.entity.Hive;
import com.example.beehiveproject.entity.User;
import com.example.beehiveproject.entity.HiveAlert;
import com.example.beehiveproject.repository.HiveAlertRepository;
import com.example.beehiveproject.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hive-alerts")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class HiveAlertController {
    @Autowired
    private HiveAlertRepository hiveAlertRepository;
    @Autowired
    private JwtUtil jwtUtil;

    // Tüm uyarılar (admin veya genel liste)
    @GetMapping
    public ResponseEntity<List<HiveAlertResponse>> getAllAlerts() {
        List<HiveAlert> alerts = hiveAlertRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<HiveAlertResponse> response = alerts.stream().map(alert -> new HiveAlertResponse(
                alert.getId(),
                alert.getHive() != null ? alert.getHive().getId() : null,
                alert.getHive() != null ? alert.getHive().getName() : null,
                alert.getMessage(),
                alert.getType(),
                alert.getCreatedAt() != null ? alert.getCreatedAt().format(formatter) : null,
                alert.isRead(),
                alert.getUser() != null ? alert.getUser().getId() : null
        )).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // Giriş yapan kullanıcıya ait uyarılar
    @GetMapping("/my-alerts")
    public ResponseEntity<List<HiveAlertResponse>> getMyAlerts(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        List<HiveAlert> alerts = hiveAlertRepository.findByUser_Id(userId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<HiveAlertResponse> response = alerts.stream().map(alert -> new HiveAlertResponse(
                alert.getId(),
                alert.getHive() != null ? alert.getHive().getId() : null,
                alert.getHive() != null ? alert.getHive().getName() : null,
                alert.getMessage(),
                alert.getType(),
                alert.getCreatedAt() != null ? alert.getCreatedAt().format(formatter) : null,
                alert.isRead(),
                alert.getUser() != null ? alert.getUser().getId() : null
        )).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // Uyarıyı okundu olarak işaretle
    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAlertAsRead(@PathVariable Long id) {
        HiveAlert alert = hiveAlertRepository.findById(id).orElseThrow();
        alert.setRead(true);
        hiveAlertRepository.save(alert);
        return ResponseEntity.ok().build();
    }

    // Uyarıyı sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        hiveAlertRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Uyarı ekle (POST)
    @PostMapping
    public ResponseEntity<HiveAlertResponse> createAlert(@RequestBody HiveAlert alert, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        com.example.beehiveproject.entity.User user = new com.example.beehiveproject.entity.User();
        user.setId(userId);
        alert.setUser(user);
        alert.setCreatedAt(java.time.LocalDateTime.now());
        HiveAlert saved = hiveAlertRepository.save(alert);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        HiveAlertResponse response = new HiveAlertResponse(
                saved.getId(),
                saved.getHive() != null ? saved.getHive().getId() : null,
                saved.getHive() != null ? saved.getHive().getName() : null,
                saved.getMessage(),
                saved.getType(),
                saved.getCreatedAt() != null ? saved.getCreatedAt().format(formatter) : null,
                saved.isRead(),
                saved.getUser() != null ? saved.getUser().getId() : null
        );
        return ResponseEntity.ok(response);
    }
}
