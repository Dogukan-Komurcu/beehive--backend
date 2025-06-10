package com.example.beehiveproject.controller;

import com.example.beehiveproject.dto.request.CreateDiseaseRecordRequest;
import com.example.beehiveproject.dto.response.HiveResponse;
import com.example.beehiveproject.dto.response.DiseaseRecordResponse;
import com.example.beehiveproject.entity.DiseaseRecord;
import com.example.beehiveproject.entity.Hive;
import com.example.beehiveproject.service.DiseaseRecordService;
import com.example.beehiveproject.service.HiveService;
import com.example.beehiveproject.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RestController
@RequestMapping("/api/diseases")
public class DiseaseRecordController {
    @Autowired
    private DiseaseRecordService diseaseRecordService;
    @Autowired
    private HiveService hiveService;
    @Autowired
    private JwtUtil jwtUtil;

    // Kullanıcının hasta kovanlarını getir (DTO ile)
    @GetMapping("/my-diseased-hives")
    public List<HiveResponse> getMyDiseasedHives(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        List<Hive> hives = diseaseRecordService.getUserDiseasedHives(userId);
        return hives.stream().map(hiveService::toResponse).collect(Collectors.toList());
    }

    // Kullanıcının hastalık kayıtlarını getir (DTO ile)
    @GetMapping("/my-records")
    public List<DiseaseRecordResponse> getMyDiseaseRecords(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        List<DiseaseRecord> records = diseaseRecordService.getUserDiseaseRecords(userId);
        return records.stream().map(DiseaseRecordResponse::fromEntity).collect(Collectors.toList());
    }

    // Hastalık kaydı oluştur
    @PostMapping
    public DiseaseRecordResponse createDiseaseRecord(@RequestBody CreateDiseaseRecordRequest request, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        DiseaseRecord record = diseaseRecordService.createDiseaseRecord(request, userId);
        return DiseaseRecordResponse.fromEntity(record);
    }

    // Kovanı iyileştir (hastalık kaydını sil)
    @DeleteMapping("/heal-hive/{hiveId}")
    public void healHive(@PathVariable Long hiveId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        diseaseRecordService.deleteDiseaseRecordForHive(hiveId, userId);
    }

    @DeleteMapping("/my-diseased-hives/{hiveId}")
    public void deleteDiseaseRecordByHive(@PathVariable Long hiveId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        diseaseRecordService.deleteDiseaseRecordByHiveIdAndUserId(hiveId, userId);
    }

    @GetMapping("/my-diseased-hives/colony/{colonyId}")
    public List<HiveResponse> getMyDiseasedHivesByColony(@PathVariable Long colonyId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        List<Hive> hives = diseaseRecordService.getUserDiseasedHivesByColony(userId, colonyId);
        return hives.stream().map(hiveService::toResponse).collect(Collectors.toList());
    }
} 