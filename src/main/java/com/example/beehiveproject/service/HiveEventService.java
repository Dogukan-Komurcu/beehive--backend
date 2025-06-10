package com.example.beehiveproject.service;

import com.example.beehiveproject.entity.HiveEvent;
import com.example.beehiveproject.entity.Hive;
import com.example.beehiveproject.entity.User;
import com.example.beehiveproject.repository.HiveEventRepository;
import com.example.beehiveproject.repository.HiveRepository;
import com.example.beehiveproject.repository.UserRepository;
import com.example.beehiveproject.dto.request.HiveEventCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HiveEventService {
    @Autowired
    private HiveEventRepository hiveEventRepository;
    @Autowired
    private HiveRepository hiveRepository;
    @Autowired
    private UserRepository userRepository;

    public HiveEvent createHiveEvent(HiveEventCreateRequest request, String userId) {
        HiveEvent event = new HiveEvent();
        event.setEventType(request.getEventType());
        event.setDescription(request.getDescription());
        event.setEventDate(LocalDateTime.now());
        Hive hive = hiveRepository.findById(request.getHiveId()).orElse(null);
        event.setHive(hive);
        User user = userRepository.findById(userId).orElse(null);
        event.setUser(user);
        return hiveEventRepository.save(event);
    }

    public List<HiveEvent> getUserHiveEvents(String userId) {
        return hiveEventRepository.findAllByUser_Id(userId);
    }

    public HiveEvent updateHiveEvent(Long eventId, HiveEventCreateRequest request, String userId) {
        HiveEvent event = hiveEventRepository.findById(eventId).orElseThrow();
        if (event.getUser() == null || !event.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu kaydı güncelleme yetkiniz yok.");
        }
        if (request.getEventType() != null) event.setEventType(request.getEventType());
        if (request.getDescription() != null) event.setDescription(request.getDescription());
        if (request.getHiveId() != null) {
            Hive hive = hiveRepository.findById(request.getHiveId()).orElse(null);
            event.setHive(hive);
        }
        return hiveEventRepository.save(event);
    }

    public void deleteHiveEvent(Long eventId, String userId) {
        HiveEvent event = hiveEventRepository.findById(eventId).orElseThrow();
        if (event.getUser() == null || !event.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu kaydı silme yetkiniz yok.");
        }
        hiveEventRepository.delete(event);
    }
} 