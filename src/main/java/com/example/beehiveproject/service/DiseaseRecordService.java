package com.example.beehiveproject.service;

import com.example.beehiveproject.entity.DiseaseRecord;
import com.example.beehiveproject.entity.User;
import com.example.beehiveproject.entity.Colony;
import com.example.beehiveproject.entity.Hive;
import com.example.beehiveproject.repository.DiseaseRecordRepository;
import com.example.beehiveproject.repository.ColonyRepository;
import com.example.beehiveproject.repository.HiveRepository;
import com.example.beehiveproject.repository.UserRepository;
import com.example.beehiveproject.dto.request.CreateDiseaseRecordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiseaseRecordService {
    @Autowired
    private DiseaseRecordRepository diseaseRecordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ColonyRepository colonyRepository;
    @Autowired
    private HiveRepository hiveRepository;

    public DiseaseRecord createDiseaseRecord(CreateDiseaseRecordRequest request, String userId) {
        DiseaseRecord record = new DiseaseRecord();
        record.setDiseaseName(request.getDiseaseName());
        record.setDescription(request.getDescription());
        record.setCreatedAt(LocalDateTime.now());
        User user = userRepository.findById(userId).orElseThrow();
        record.setUser(user);
        Colony colony = colonyRepository.findById(request.getColonyId()).orElse(null);
        record.setColony(colony);
        List<Hive> hives = request.getHiveIds().stream()
            .map(id -> hiveRepository.findById(id).orElse(null))
            .filter(h -> h != null && h.getUser().getId().equals(userId))
            .collect(Collectors.toList());
        record.setHives(hives);
        return diseaseRecordRepository.save(record);
    }

    public List<Hive> getUserDiseasedHives(String userId) {
        // Kullanıcının hasta olan kovanlarını getir
        List<DiseaseRecord> records = diseaseRecordRepository.findAllByUser_Id(userId);
        return records.stream()
            .flatMap(r -> r.getHives().stream())
            .distinct()
            .collect(Collectors.toList());
    }

    public List<DiseaseRecord> getUserDiseaseRecords(String userId) {
        return diseaseRecordRepository.findAllByUser_Id(userId);
    }

    public void deleteDiseaseRecordForHive(Long hiveId, String userId) {
        List<DiseaseRecord> records = diseaseRecordRepository.findAllByUser_Id(userId);
        for (DiseaseRecord record : records) {
            if (record.getHives() != null && record.getHives().stream().anyMatch(h -> h.getId().equals(hiveId))) {
                // Eğer sadece bir kovan varsa, tüm kaydı sil
                if (record.getHives().size() == 1) {
                    diseaseRecordRepository.delete(record);
                } else {
                    // Birden fazla kovan varsa, sadece ilgili kovani listeden çıkar ve kaydı güncelle
                    record.getHives().removeIf(h -> h.getId().equals(hiveId));
                    diseaseRecordRepository.save(record);
                }
            }
        }
    }

    public void deleteDiseaseRecordByHiveIdAndUserId(Long hiveId, String userId) {
        List<DiseaseRecord> records = diseaseRecordRepository.findAllByUser_Id(userId);
        for (DiseaseRecord record : records) {
            List<Hive> hives = record.getHives();
            boolean removed = hives.removeIf(h -> h.getId().equals(hiveId));
            if (removed) {
                if (hives.isEmpty()) {
                    diseaseRecordRepository.delete(record);
                } else {
                    record.setHives(hives);
                    diseaseRecordRepository.save(record);
                }
            }
        }
    }

    public List<Hive> getUserDiseasedHivesByColony(String userId, Long colonyId) {
        List<DiseaseRecord> records = diseaseRecordRepository.findAllByUser_Id(userId);
        return records.stream()
            .filter(r -> r.getColony() != null && r.getColony().getKoloniId().equals(colonyId))
            .flatMap(r -> r.getHives().stream())
            .distinct()
            .collect(Collectors.toList());
    }
} 