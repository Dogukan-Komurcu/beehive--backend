package com.example.beehiveproject.service;

import com.example.beehiveproject.dto.response.DashboardResponse;
import com.example.beehiveproject.dto.response.LocationDTO;
import com.example.beehiveproject.dto.response.YearlyProductionDTO;
import com.example.beehiveproject.entity.Hive;
import com.example.beehiveproject.repository.HiveRepository;
import com.example.beehiveproject.repository.BakimGoreviRepository;
import com.example.beehiveproject.entity.BakimGorevi;
import com.example.beehiveproject.repository.UserRepository;
import com.example.beehiveproject.repository.ColonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class DashboardService {

    @Autowired
    private HiveRepository hiveRepository;

    @Autowired
    private BakimGoreviRepository bakimGoreviRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ColonyRepository colonyRepository;

    public DashboardResponse getDashboardData() {
        int totalHives = (int) hiveRepository.count();
        int infectedHives = (int) hiveRepository.countByIsInfectedTrue();
        List<BakimGorevi> incompleteTasks = bakimGoreviRepository.findByTamamlandiFalse();
        int maintenanceNeeded = incompleteTasks.size();
        double totalProduction = hiveRepository.sumTotalHoneyProduction() != null
                ? hiveRepository.sumTotalHoneyProduction() : 0;

        List<LocationDTO> locations = hiveRepository.findAll().stream()
                .map(hive -> new LocationDTO(hive.getLatitude(), hive.getLongitude()))
                .collect(Collectors.toList());

        List<YearlyProductionDTO> yearlyProduction = hiveRepository.getYearlyProduction();

        return new DashboardResponse(totalHives, infectedHives, maintenanceNeeded, totalProduction, locations, yearlyProduction, incompleteTasks);
    }

    public long getTotalUserCount() {
        return userRepository.count();
    }

    public long getTotalHiveCount() {
        return hiveRepository.count();
    }

    public long getUsedHiveCount() {
        return hiveRepository.countByDurum("dolu");
    }

    public long getDailyHiveCount(LocalDateTime start, LocalDateTime end) {
        return hiveRepository.findAll().stream().filter(h -> h.getOlusturmaTarihi() != null && h.getOlusturmaTarihi().isAfter(start) && h.getOlusturmaTarihi().isBefore(end)).count();
    }

    public long getDailyColonyCount(LocalDateTime start, LocalDateTime end) {
        return colonyRepository.findAll().stream().filter(c -> c.getOlusturmaTarihi() != null && c.getOlusturmaTarihi().isAfter(start) && c.getOlusturmaTarihi().isBefore(end)).count();
    }
}