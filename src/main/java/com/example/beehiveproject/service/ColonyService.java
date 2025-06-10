package com.example.beehiveproject.service;

import com.example.beehiveproject.entity.Colony;
import com.example.beehiveproject.repository.ColonyRepository;
import com.example.beehiveproject.dto.request.ColonyCreateRequest;
import com.example.beehiveproject.dto.response.ColonyResponse;
import com.example.beehiveproject.dto.request.UpdateColonyRequest;
import com.example.beehiveproject.repository.HiveRepository;
import com.example.beehiveproject.entity.Hive;
import com.example.beehiveproject.entity.User;
import com.example.beehiveproject.repository.UserRepository;
import com.example.beehiveproject.repository.DiseaseRecordRepository;
import com.example.beehiveproject.repository.HiveEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColonyService {
    private final ColonyRepository colonyRepository;
    private final HiveRepository hiveRepository;
    private final UserRepository userRepository;
    private final DiseaseRecordRepository diseaseRecordRepository;
    private final HiveEventRepository hiveEventRepository;

    public ColonyService(
        ColonyRepository colonyRepository,
        HiveRepository hiveRepository,
        UserRepository userRepository,
        DiseaseRecordRepository diseaseRecordRepository,
        HiveEventRepository hiveEventRepository
    ) {
        this.colonyRepository = colonyRepository;
        this.hiveRepository = hiveRepository;
        this.userRepository = userRepository;
        this.diseaseRecordRepository = diseaseRecordRepository;
        this.hiveEventRepository = hiveEventRepository;
    }

    public ColonyResponse createColony(ColonyCreateRequest request) {
        Colony colony = new Colony();
        colony.setAd(request.getAd());
        colony.setAnaAriTuru(request.getAnaAriTuru());
        colony.setOlusturmaTarihi(request.getOlusturmaTarihi());
        colony.setSaglikDurumu(request.getSaglikDurumu());
        colony.setDurum(request.getDurum());
        colony.setNotlar(request.getNotlar());
        // kaynakKoloniId ile kaynakKoloni set edilecek
        if (request.getKaynakKoloniId() != null) {
            colony.setKaynakKoloni(colonyRepository.findById(request.getKaynakKoloniId()).orElse(null));
        }
        // Kullanıcıyı ata
        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId()).orElse(null);
            colony.setUser(user);
        }
        Colony saved = colonyRepository.save(colony);
        ColonyResponse response = new ColonyResponse();
        response.setKoloniId(saved.getKoloniId());
        response.setAd(saved.getAd());
        response.setAnaAriTuru(saved.getAnaAriTuru());
        response.setOlusturmaTarihi(saved.getOlusturmaTarihi());
        response.setBolunmeTarihi(saved.getBolunmeTarihi());
        response.setSaglikDurumu(saved.getSaglikDurumu());
        response.setDurum(saved.getDurum());
        response.setNotlar(saved.getNotlar());
        response.setKaynakKoloniId(saved.getKaynakKoloni() != null ? saved.getKaynakKoloni().getKoloniId() : null);
        response.setUserId(saved.getUser() != null ? saved.getUser().getId() : null);
        return response;
    }

    public Optional<Colony> getColonyById(Long id) {
        return colonyRepository.findById(id);
    }

    public List<ColonyResponse> getAllColonies(String userId) {
        return colonyRepository.findAll().stream()
            .filter(colony -> colony.getUser() != null && colony.getUser().getId().equals(userId))
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private ColonyResponse toResponse(Colony colony) {
        ColonyResponse response = new ColonyResponse();
        response.setKoloniId(colony.getKoloniId());
        response.setAd(colony.getAd());
        response.setAnaAriTuru(colony.getAnaAriTuru());
        response.setOlusturmaTarihi(colony.getOlusturmaTarihi());
        response.setBolunmeTarihi(colony.getBolunmeTarihi());
        response.setSaglikDurumu(colony.getSaglikDurumu());
        response.setDurum(colony.getDurum());
        response.setNotlar(colony.getNotlar());
        return response;
    }

    public Colony updateColony(Long id, UpdateColonyRequest request) {
        Colony colony = colonyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Colony not found"));
        if (request.getAd() != null) colony.setAd(request.getAd());
        if (request.getAnaAriTuru() != null) colony.setAnaAriTuru(request.getAnaAriTuru());
        if (request.getOlusturmaTarihi() != null) colony.setOlusturmaTarihi(request.getOlusturmaTarihi());
        if (request.getBolunmeTarihi() != null) colony.setBolunmeTarihi(request.getBolunmeTarihi());
        if (request.getSaglikDurumu() != null) colony.setSaglikDurumu(request.getSaglikDurumu());
        if (request.getDurum() != null) colony.setDurum(request.getDurum());
        if (request.getNotlar() != null) colony.setNotlar(request.getNotlar());
        if (request.getKaynakKoloniId() != null) {
            colony.setKaynakKoloni(colonyRepository.findById(request.getKaynakKoloniId()).orElse(null));
        }
        return colonyRepository.save(colony);
    }

    public void deleteColony(Long id) {
        // Önce bu koloniye bağlı kovanların koloni referansını null yap
        List<Hive> hives = hiveRepository.findAllByColonyKoloniId(id);
        for (Hive hive : hives) {
            // Hive'a bağlı eventleri sil
            hiveEventRepository.findAllByHive_Id(hive.getId()).forEach(hiveEventRepository::delete);
            hive.setColony(null);
            hiveRepository.save(hive);
        }
        // Koloniye bağlı DiseaseRecord'ları sil
        diseaseRecordRepository.findAllByColony_KoloniId(id).forEach(diseaseRecordRepository::delete);
        // Sonra koloniyi sil
        colonyRepository.deleteById(id);
    }

    // Diğer işlemler: bölme, birleştirme, sağlık kaydı, ölüm/çöküş, taşıma vs. burada eklenebilir
} 