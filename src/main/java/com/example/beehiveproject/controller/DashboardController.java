package com.example.beehiveproject.controller;

import com.example.beehiveproject.dto.response.DashboardResponse;
import com.example.beehiveproject.dto.response.HiveResponse;
import com.example.beehiveproject.security.JwtUtil;
import com.example.beehiveproject.service.DashboardService;
import com.example.beehiveproject.service.HiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private HiveService hiveService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboardData() {
        DashboardResponse response = dashboardService.getDashboardData();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats/total-users")
    public long getTotalUsers() {
        return dashboardService.getTotalUserCount();
    }

    @GetMapping("/stats/total-hives")
    public long getTotalHives() {
        return dashboardService.getTotalHiveCount();
    }

    @GetMapping("/stats/used-hives")
    public long getUsedHives() {
        return dashboardService.getUsedHiveCount();
    }

    @GetMapping("/stats/daily-hives")
    public long getDailyHives(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return dashboardService.getDailyHiveCount(start, end);
    }

    @GetMapping("/stats/daily-colonies")
    public long getDailyColonies(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return dashboardService.getDailyColonyCount(start, end);
    }

    @GetMapping("/my-hives")
    public ResponseEntity<List<HiveResponse>> getMyHives(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        List<HiveResponse> hives = hiveService.getAllHivesByUserId(userId);
        return ResponseEntity.ok(hives);
    }

    // Kullanıcı veya admin dashboard'u: rol kontrolü ile yönlendirme
    @GetMapping("/role-dashboard")
    public ResponseEntity<?> getDashboardByRole(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String role = jwtUtil.extractRole(token);
        if ("ADMIN".equalsIgnoreCase(role)) {
            // Admin dashboard datası
            DashboardResponse response = dashboardService.getDashboardData();
            return ResponseEntity.ok().body(response); // Burada admin'e özel response dönebilirsin
        } else {
            // Kullanıcı dashboard datası
            DashboardResponse response = dashboardService.getDashboardData();
            return ResponseEntity.ok().body(response); // Burada kullanıcıya özel response dönebilirsin
        }
    }
}