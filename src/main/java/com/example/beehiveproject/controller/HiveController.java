package com.example.beehiveproject.controller;

import com.example.beehiveproject.dto.request.HiveCreateRequest;
import com.example.beehiveproject.dto.request.HiveUpdateRequest;
import com.example.beehiveproject.dto.request.MoveHivesRequest;
import com.example.beehiveproject.dto.request.SplitHiveRequest;
import com.example.beehiveproject.dto.request.MergeHivesRequest;
import com.example.beehiveproject.dto.response.HiveResponse;
import com.example.beehiveproject.service.HiveService;
import com.example.beehiveproject.security.JwtUtil;
import com.example.beehiveproject.entity.User;
import com.example.beehiveproject.repository.UserRepository;
import com.example.beehiveproject.entity.HiveSensorData;
import com.example.beehiveproject.entity.HiveAlert;
import com.example.beehiveproject.repository.HiveAlertRepository;
import com.example.beehiveproject.dto.response.HiveSensorDataResponse;
import com.example.beehiveproject.dto.response.HiveAlertResponse;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RestController
@RequestMapping("/api/hives")
public class HiveController {
    @Autowired
    private HiveService hiveService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HiveAlertRepository hiveAlertRepository;

    @GetMapping
    public List<HiveResponse> getAllHives() {
        return hiveService.getAllHives();
    }

    @GetMapping("/filter")
    public List<HiveResponse> getHivesByLocationBox(
            @RequestParam Double minLat,
            @RequestParam Double maxLat,
            @RequestParam Double minLng,
            @RequestParam Double maxLng
    ) {
        return hiveService.getHivesByLocationBox(minLat, maxLat, minLng, maxLng);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HiveResponse> getHiveById(@PathVariable Long id) {
        HiveResponse response = hiveService.getHiveById(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public HiveResponse createHive(@RequestBody HiveCreateRequest request, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        User user = userRepository.findById(userId).orElseThrow();
        return hiveService.createHive(request, user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HiveResponse> updateHive(@PathVariable Long id, @RequestBody HiveUpdateRequest request, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        User user = userRepository.findById(userId).orElseThrow();
        HiveResponse response = hiveService.updateHive(id, request, user);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHive(@PathVariable Long id) {
        hiveService.deleteHive(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public List<HiveResponse> getMyHives(@RequestHeader("Authorization") String authHeader) {
        // Bearer <token> formatında gelir
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        return hiveService.getAllHivesByUserId(userId);
    }

    @PostMapping("/move")
    public ResponseEntity<Void> moveHivesToColony(@RequestBody MoveHivesRequest request) {
        hiveService.moveHivesToColony(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/split")
    public ResponseEntity<HiveResponse> splitHive(@PathVariable Long id, @RequestBody SplitHiveRequest request) {
        HiveResponse response = hiveService.splitHive(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/merge")
    public ResponseEntity<HiveResponse> mergeHives(@RequestBody MergeHivesRequest request) {
        HiveResponse response = hiveService.mergeHives(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/colony/{colonyId}")
    public List<HiveResponse> getHivesByColony(@PathVariable Long colonyId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        return hiveService.getHivesByColonyIdAndUserId(colonyId, userId);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<HiveResponse> changeHiveStatus(@PathVariable Long id, @RequestParam String status) {
        HiveResponse response = hiveService.changeHiveStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/sensor-data")
    public HiveSensorData addSensorData(@PathVariable Long id, @RequestBody HiveSensorData data) {
        return hiveService.addSensorData(id, data);
    }

    @GetMapping("/{id}/sensor-data")
    public List<HiveSensorDataResponse> getSensorData(@PathVariable Long id) {
        List<HiveSensorData> dataList = hiveService.getSensorData(id);
        List<HiveSensorDataResponse> responseList = new java.util.ArrayList<>();
        for (HiveSensorData data : dataList) {
            HiveSensorDataResponse dto = new HiveSensorDataResponse();            dto.setId(data.getId());
            dto.setHiveId(data.getHive() != null ? data.getHive().getId() : null);
            dto.setTemperature(data.getTemperature());
            dto.setHumidity(data.getHumidity());
            dto.setBattery(data.getBattery());
            dto.setActivity(data.getActivity());
            dto.setMotion(data.getMotion());
            dto.setTimestamp(data.getTimestamp());
            responseList.add(dto);
        }
        return responseList;
    }

    // Kullanıcının tüm uyarılarını getir
    @GetMapping("/alerts/my")
    public List<HiveAlertResponse> getMyAlerts(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        List<HiveAlert> alerts = hiveAlertRepository.findByUser_Id(userId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return alerts.stream().map(alert -> new HiveAlertResponse(
                alert.getId(),
                alert.getHive() != null ? alert.getHive().getId() : null,
                alert.getHive() != null ? alert.getHive().getName() : null,
                alert.getMessage(),
                alert.getType(),
                alert.getCreatedAt() != null ? alert.getCreatedAt().format(formatter) : null,
                alert.isRead(),
                alert.getUser() != null ? alert.getUser().getId() : null
        )).collect(Collectors.toList());
    }

    // Belirli bir kovanın uyarılarını getir
    @GetMapping("/{id}/alerts")
    public List<HiveAlertResponse> getHiveAlerts(@PathVariable Long id) {
        List<HiveAlert> alerts = hiveAlertRepository.findByHive_Id(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return alerts.stream().map(alert -> new HiveAlertResponse(
                alert.getId(),
                alert.getHive() != null ? alert.getHive().getId() : null,
                alert.getHive() != null ? alert.getHive().getName() : null,
                alert.getMessage(),
                alert.getType(),
                alert.getCreatedAt() != null ? alert.getCreatedAt().format(formatter) : null,
                alert.isRead(),
                alert.getUser() != null ? alert.getUser().getId() : null
        )).collect(Collectors.toList());
    }

    // Uyarıyı okundu olarak işaretle
    @PutMapping("/alerts/{alertId}/read")
    public HiveAlert markAlertAsRead(@PathVariable Long alertId) {
        HiveAlert alert = hiveAlertRepository.findById(alertId).orElseThrow();
        alert.setRead(true);
        return hiveAlertRepository.save(alert);
    }

    // Uyarıyı kapat
    @PutMapping("/alerts/{alertId}/close")
    public HiveAlert closeAlert(@PathVariable Long alertId) {
        HiveAlert alert = hiveAlertRepository.findById(alertId).orElseThrow();
        alert.setClosed(true);
        return hiveAlertRepository.save(alert);
    }

    // Kullanıcının tüm uyarılarını okundu olarak işaretle
    @PutMapping("/alerts/my/read-all")
    public void markAllMyAlertsAsRead(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        List<HiveAlert> alerts = hiveAlertRepository.findByUser_Id(userId);
        for (HiveAlert alert : alerts) {
            alert.setRead(true);
        }
        hiveAlertRepository.saveAll(alerts);
    }
}