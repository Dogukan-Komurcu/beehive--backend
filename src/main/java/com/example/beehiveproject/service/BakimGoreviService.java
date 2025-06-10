package com.example.beehiveproject.service;

import com.example.beehiveproject.entity.BakimGorevi;
import com.example.beehiveproject.entity.Colony;
import com.example.beehiveproject.entity.Hive;
import com.example.beehiveproject.repository.BakimGoreviRepository;
import com.example.beehiveproject.repository.ColonyRepository;
import com.example.beehiveproject.repository.HiveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BakimGoreviService {
    private final BakimGoreviRepository repository;
    private final ColonyRepository colonyRepository;
    private final HiveRepository hiveRepository;

    public BakimGoreviService(BakimGoreviRepository repository, ColonyRepository colonyRepository, HiveRepository hiveRepository) {
        this.repository = repository;
        this.colonyRepository = colonyRepository;
        this.hiveRepository = hiveRepository;
    }

    public List<BakimGorevi> getAll() {
        return repository.findAll();
    }

    public BakimGorevi save(BakimGorevi gorev) {
        return repository.save(gorev);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public BakimGorevi update(Long id, BakimGorevi updated) {
        BakimGorevi gorev = repository.findById(id).orElseThrow();
        gorev.setBaslik(updated.getBaslik());
        gorev.setAciklama(updated.getAciklama());
        gorev.setTarih(updated.getTarih());
        gorev.setTamamlandi(updated.isTamamlandi());
        return repository.save(gorev);
    }

    public BakimGorevi createForColony(Long koloniId, BakimGorevi gorev) {
        Colony koloni = colonyRepository.findById(koloniId).orElseThrow();
        gorev.setKoloni(koloni);
        gorev.setKovan(null);
        return repository.save(gorev);
    }

    public BakimGorevi createForHive(Long kovanId, BakimGorevi gorev) {
        Hive kovan = hiveRepository.findById(kovanId).orElseThrow();
        gorev.setKovan(kovan);
        gorev.setKoloni(null);
        return repository.save(gorev);
    }

    public BakimGorevi markAsCompleted(Long id) {
        BakimGorevi gorev = repository.findById(id).orElseThrow();
        gorev.setTamamlandi(true);
        return repository.save(gorev);
    }
} 