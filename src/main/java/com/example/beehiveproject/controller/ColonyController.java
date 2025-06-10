package com.example.beehiveproject.controller;

import com.example.beehiveproject.entity.Colony;
import com.example.beehiveproject.service.ColonyService;
import com.example.beehiveproject.dto.request.ColonyCreateRequest;
import com.example.beehiveproject.dto.response.ColonyResponse;
import com.example.beehiveproject.dto.request.UpdateColonyRequest;
import com.example.beehiveproject.security.JwtUtil;
import com.example.beehiveproject.entity.User;
import com.example.beehiveproject.repository.UserRepository;
import com.example.beehiveproject.entity.Hive;
import com.example.beehiveproject.repository.HiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RestController
@RequestMapping("/api/colonies")
public class ColonyController {
    @Autowired
    private ColonyService colonyService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HiveRepository hiveRepository;

    public ColonyController(ColonyService colonyService, JwtUtil jwtUtil, UserRepository userRepository, HiveRepository hiveRepository) {
        this.colonyService = colonyService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.hiveRepository = hiveRepository;
    }

    @PostMapping
    public ResponseEntity<ColonyResponse> createColony(@RequestBody ColonyCreateRequest request, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        request.setUserId(userId);
        return ResponseEntity.ok(colonyService.createColony(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colony> getColonyById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        return colonyService.getColonyById(id)
                .filter(colony -> colony.getUser() != null && colony.getUser().getId().equals(userId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ColonyResponse> getAllColonies(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        return colonyService.getAllColonies(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colony> updateColony(@PathVariable Long id, @RequestBody UpdateColonyRequest request, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        Colony colony = colonyService.getColonyById(id).orElse(null);
        if (colony == null || colony.getUser() == null || !colony.getUser().getId().equals(userId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(colonyService.updateColony(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColony(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        Colony colony = colonyService.getColonyById(id).orElse(null);
        if (colony == null || colony.getUser() == null || !colony.getUser().getId().equals(userId)) {
            return ResponseEntity.notFound().build();
        }
        colonyService.deleteColony(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/hives")
    public List<Hive> getHivesByColony(@PathVariable Long id) {
        return hiveRepository.findAllByColonyKoloniId(id);
    }

    // Diğer işlemler: bölme, birleştirme, sağlık kaydı, ölüm/çöküş, taşıma vs. burada eklenebilir
} 