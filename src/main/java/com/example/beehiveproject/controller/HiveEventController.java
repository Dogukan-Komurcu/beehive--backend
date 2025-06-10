package com.example.beehiveproject.controller;

import com.example.beehiveproject.dto.request.HiveEventCreateRequest;
import com.example.beehiveproject.entity.HiveEvent;
import com.example.beehiveproject.service.HiveEventService;
import com.example.beehiveproject.security.JwtUtil;
import com.example.beehiveproject.dto.response.HiveEventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RestController
@RequestMapping("/api/hive-events")
public class HiveEventController {
    @Autowired
    private HiveEventService hiveEventService;
    @Autowired
    private JwtUtil jwtUtil;

    // Kullanıcının ölüm/çöküş kayıtlarını getir
    @GetMapping("/my")
    public List<HiveEventResponse> getMyHiveEvents(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        return hiveEventService.getUserHiveEvents(userId)
            .stream()
            .map(HiveEventResponse::fromEntity)
            .collect(Collectors.toList());
    }

    // Ölüm/çöküş bildir
    @PostMapping
    public HiveEventResponse createHiveEvent(@RequestBody HiveEventCreateRequest request, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        return HiveEventResponse.fromEntity(hiveEventService.createHiveEvent(request, userId));
    }

    @PatchMapping("/{eventId}")
    public HiveEventResponse updateHiveEvent(@PathVariable Long eventId, @RequestBody HiveEventCreateRequest request, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        return HiveEventResponse.fromEntity(hiveEventService.updateHiveEvent(eventId, request, userId));
    }

    @DeleteMapping("/{eventId}")
    public void deleteHiveEvent(@PathVariable Long eventId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        hiveEventService.deleteHiveEvent(eventId, userId);
    }
} 