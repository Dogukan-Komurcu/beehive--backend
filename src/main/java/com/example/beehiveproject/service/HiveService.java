package com.example.beehiveproject.service;

import com.example.beehiveproject.dto.request.HiveCreateRequest;
import com.example.beehiveproject.dto.request.HiveUpdateRequest;
import com.example.beehiveproject.dto.request.MoveHivesRequest;
import com.example.beehiveproject.dto.request.SplitHiveRequest;
import com.example.beehiveproject.dto.request.MergeHivesRequest;
import com.example.beehiveproject.dto.response.HiveResponse;
import com.example.beehiveproject.entity.Hive;
import com.example.beehiveproject.entity.User;
import com.example.beehiveproject.entity.Colony;
import com.example.beehiveproject.entity.HiveSensorData;
import com.example.beehiveproject.entity.HiveAlert;
import com.example.beehiveproject.repository.HiveRepository;
import com.example.beehiveproject.repository.ColonyRepository;
import com.example.beehiveproject.repository.HiveSensorDataRepository;
import com.example.beehiveproject.repository.HiveAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HiveService {
    @Autowired
    private HiveRepository hiveRepository;

    @Autowired
    private ColonyRepository colonyRepository;

    @Autowired
    private HiveSensorDataRepository hiveSensorDataRepository;

    @Autowired
    private HiveAlertRepository hiveAlertRepository;

    public List<HiveResponse> getAllHives() {
        return hiveRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<HiveResponse> getHivesByLocationBox(Double minLat, Double maxLat, Double minLng, Double maxLng) {
        return hiveRepository.findByLocationBox(minLat, maxLat, minLng, maxLng)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public HiveResponse getHiveById(Long id) {
        return hiveRepository.findById(id).map(this::toResponse).orElse(null);
    }

    public HiveResponse createHive(HiveCreateRequest request, User user) {
        Hive hive = new Hive();
        hive.setName(request.getName());
        hive.setKonum(request.getKonum());
        hive.setDurum(request.getDurum());
        hive.setKonumLat(request.getKonumLat());
        hive.setKonumLng(request.getKonumLng());
        if (request.getKoloniId() != null) {
            Colony colony = colonyRepository.findById(request.getKoloniId()).orElse(null);
            hive.setColony(colony);
        } else {
            hive.setColony(null);
        }
        hive.setAciklama(request.getAciklama());
        hive.setOlusturmaTarihi(LocalDateTime.now());
        hive.setUser(user);
        hive.setEstimatedBeeCount(request.getEstimatedBeeCount());
        hive.setInstallDate(request.getInstallDate());
        return toResponse(hiveRepository.save(hive));
    }

    public HiveResponse updateHive(Long id, HiveUpdateRequest request, com.example.beehiveproject.entity.User user) {
        Optional<Hive> optionalHive = hiveRepository.findById(id);
        if (optionalHive.isEmpty()) return null;
        Hive hive = optionalHive.get();
        if (!hive.getUser().getId().equals(user.getId())) return null;
        hive.setName(request.getName());
        hive.setKonum(request.getKonum());
        hive.setDurum(request.getDurum());
        if (request.getKoloniId() != null) {
            Colony colony = colonyRepository.findById(request.getKoloniId()).orElse(null);
            hive.setColony(colony);
        } else {
            hive.setColony(null);
        }
        hive.setAciklama(request.getAciklama());
        hive.setInstallDate(request.getInstallDate());
        return toResponse(hiveRepository.save(hive));
    }

    // Admin: Başka bir kullanıcının kovanını güncelleyebilir
    public HiveResponse updateHiveAsAdmin(Long hiveId, HiveUpdateRequest request, User user) {
        Optional<Hive> optionalHive = hiveRepository.findById(hiveId);
        if (optionalHive.isEmpty()) return null;
        Hive hive = optionalHive.get();
        hive.setName(request.getName());
        hive.setKonum(request.getKonum());
        hive.setDurum(request.getDurum());
        if (request.getKoloniId() != null) {
            Colony colony = colonyRepository.findById(request.getKoloniId()).orElse(null);
            hive.setColony(colony);
        } else {
            hive.setColony(null);
        }
        hive.setAciklama(request.getAciklama());
        hive.setInstallDate(request.getInstallDate());
        hive.setUser(user); // Admin başka kullanıcıya da atayabilir
        return toResponse(hiveRepository.save(hive));
    }

    public void deleteHive(Long hiveId) {
        // Önce bu kovana bağlı bölünmüş kovanların parent_hive_id'sini null yap
        List<Hive> childHives = hiveRepository.findAllByParentHiveId(hiveId);
        for (Hive child : childHives) {
            child.setParentHive(null);
            hiveRepository.save(child);
        }
        // Sonra kovanı sil
        hiveRepository.deleteById(hiveId);
    }

    public List<HiveResponse> getAllHivesByUserId(String userId) {
        return hiveRepository.findByUser_Id(userId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public void moveHivesToColony(MoveHivesRequest request) {
        Colony targetColony = colonyRepository.findById(request.getTargetColonyId())
            .orElseThrow(() -> new RuntimeException("Target colony not found"));
        for (Long hiveId : request.getHiveIds()) {
            Hive hive = hiveRepository.findById(hiveId)
                .orElseThrow(() -> new RuntimeException("Hive not found: " + hiveId));
            hive.setColony(targetColony);
            hiveRepository.save(hive);
        }
    }

    public HiveResponse splitHive(Long hiveId, SplitHiveRequest request) {
        Hive original = hiveRepository.findById(hiveId)
            .orElseThrow(() -> new RuntimeException("Kovan bulunamadı"));
        Colony targetColony = colonyRepository.findById(request.getTargetColonyId())
            .orElseThrow(() -> new RuntimeException("Hedef koloni bulunamadı"));
        Hive newHive = new Hive();
        newHive.setName(request.getNewHiveName());
        newHive.setColony(targetColony);
        newHive.setParentHive(original);
        newHive.setOlusturmaTarihi(LocalDateTime.now());
        newHive.setUser(original.getUser());
        hiveRepository.save(newHive);
        return toResponse(newHive);
    }

    public HiveResponse mergeHives(MergeHivesRequest request) {
        if (request.getHiveIds() == null || request.getHiveIds().isEmpty() || request.getHiveIds().contains(null)) {
            throw new IllegalArgumentException("Birleştirilecek kovan id'leri boş veya null olamaz!");
        }
        if (request.getTargetColonyId() == null) {
            throw new IllegalArgumentException("Hedef koloni id'si null olamaz!");
        }
        if (request.getNewHiveName() == null || request.getNewHiveName().isBlank()) {
            throw new IllegalArgumentException("Yeni kovan ismi boş olamaz!");
        }
        java.util.List<Hive> hivesToMerge = hiveRepository.findAllById(request.getHiveIds());
        if (hivesToMerge.isEmpty()) throw new RuntimeException("Birleştirilecek kovan bulunamadı!");
        Colony targetColony = colonyRepository.findById(request.getTargetColonyId())
            .orElseThrow(() -> new RuntimeException("Hedef koloni bulunamadı!"));
        Hive newHive = new Hive();
        newHive.setName(request.getNewHiveName());
        newHive.setColony(targetColony);
        newHive.setOlusturmaTarihi(java.time.LocalDateTime.now());
        newHive.setUser(hivesToMerge.get(0).getUser());
        java.util.List<String> mergedNames = hivesToMerge.stream().map(Hive::getName).toList();
        java.util.List<Long> mergedIds = hivesToMerge.stream().map(Hive::getId).toList();
        newHive.setMergedFromNames(mergedNames);
        newHive.setMergedFromIds(mergedIds);
        // Açıklama ayarla
        if (request.getAciklama() != null && !request.getAciklama().isBlank()) {
            newHive.setAciklama(request.getAciklama());
        } else {
            newHive.setAciklama("Bu kovan, " + String.join(", ", mergedNames) + " isimli kovanların birleşmesiyle oluşmuştur.");
        }
        hiveRepository.save(newHive);
        hiveRepository.deleteAll(hivesToMerge);
        return toResponse(newHive);
    }

    public List<HiveResponse> getHivesByColonyId(Long koloniId) {
        return hiveRepository.findAllByColonyKoloniId(koloniId)
            .stream().map(this::toResponse).collect(java.util.stream.Collectors.toList());
    }

    public List<HiveResponse> getHivesByColonyIdAndUserId(Long koloniId, String userId) {
        return hiveRepository.findAllByColonyKoloniIdAndUser_Id(koloniId, userId)
            .stream().map(this::toResponse).collect(java.util.stream.Collectors.toList());
    }

    public HiveResponse toResponse(Hive hive) {
        HiveResponse response = new HiveResponse();
        response.setId(hive.getId());
        response.setName(hive.getName());
        response.setKonum(hive.getKonum());
        response.setDurum(hive.getDurum());
        // Son sensör verisini ekle
        if (hive.getSensorDataList() != null && !hive.getSensorDataList().isEmpty()) {
            HiveSensorData last = hive.getSensorDataList().stream()
                .max(java.util.Comparator.comparing(HiveSensorData::getTimestamp))
                .orElse(null);
            if (last != null) {
                response.setSicaklik(last.getTemperature());
                response.setNem(last.getHumidity());
                response.setBatarya(last.getBattery());
                response.setSonVeri(last.getTimestamp() != null ? last.getTimestamp().toString() : null);
            }
        }
        response.setEstimatedBeeCount(hive.getEstimatedBeeCount());
        response.setInstallDate(hive.getInstallDate());
        return response;
    }

    // Admin: Kovan durumunu değiştir
    public HiveResponse changeHiveStatus(Long hiveId, String durum) {
        Hive hive = hiveRepository.findById(hiveId).orElseThrow();
        hive.setDurum(durum);
        return toResponse(hiveRepository.save(hive));
    }

    // Sensör verisi eklendiğinde eşik değerler aşıldıysa otomatik uyarı üret
    public void checkAndCreateAlerts(Hive hive, HiveSensorData data, User user) {
        if (data.getTemperature() != null && (data.getTemperature() < 10 || data.getTemperature() > 40)) {
            HiveAlert alert = new HiveAlert();
            alert.setHive(hive);
            alert.setUser(user);
            alert.setType("TEMPERATURE");
            alert.setMessage("Sıcaklık kritik seviyede: " + data.getTemperature());
            alert.setCreatedAt(java.time.LocalDateTime.now());
            hiveAlertRepository.save(alert);
        }
        if (data.getHumidity() != null && (data.getHumidity() < 30 || data.getHumidity() > 80)) {
            HiveAlert alert = new HiveAlert();
            alert.setHive(hive);
            alert.setUser(user);
            alert.setType("HUMIDITY");
            alert.setMessage("Nem kritik seviyede: " + data.getHumidity());
            alert.setCreatedAt(java.time.LocalDateTime.now());
            hiveAlertRepository.save(alert);
        }
        if (data.getBattery() != null && data.getBattery() < 20) {
            HiveAlert alert = new HiveAlert();
            alert.setHive(hive);
            alert.setUser(user);
            alert.setType("BATTERY");
            alert.setMessage("Pil seviyesi düşük: " + data.getBattery());
            alert.setCreatedAt(java.time.LocalDateTime.now());
            hiveAlertRepository.save(alert);
        }
        if (data.getMotion() != null && data.getMotion()) {
            HiveAlert alert = new HiveAlert();
            alert.setHive(hive);
            alert.setUser(user);
            alert.setType("MOTION");
            alert.setMessage("Kovanda hareket algılandı!");
            alert.setCreatedAt(java.time.LocalDateTime.now());
            hiveAlertRepository.save(alert);
        }
    }

    // Kovana sensör verisi ekle (otomatik uyarı üretimi ile)
    public HiveSensorData addSensorData(Long hiveId, HiveSensorData data) {
        Hive hive = hiveRepository.findById(hiveId).orElseThrow();
        data.setHive(hive);
        HiveSensorData saved = hiveSensorDataRepository.save(data);
        // Otomatik uyarı üret
        if (hive.getUser() != null) {
            checkAndCreateAlerts(hive, data, hive.getUser());
        }
        return saved;
    }

    // Kovanın tüm sensör verilerini getir
    public List<HiveSensorData> getSensorData(Long hiveId) {
        return hiveSensorDataRepository.findByHive_Id(hiveId);
    }
}