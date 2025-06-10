package com.example.beehiveproject.service;

import com.example.beehiveproject.entity.User;
import com.example.beehiveproject.repository.UserRepository;
import com.example.beehiveproject.dto.request.HiveCreateRequest;
import com.example.beehiveproject.dto.request.HiveUpdateRequest;
import com.example.beehiveproject.dto.response.HiveResponse;
import com.example.beehiveproject.entity.HiveSensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HiveService hiveService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(String id, User updated) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName(updated.getName());
        user.setFullName(updated.getFullName());
        user.setEmail(updated.getEmail());
        user.setRole(updated.getRole());
        user.setStatus(updated.getStatus());
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public User changeUserRole(String id, String role) {
        User user = userRepository.findById(id).orElseThrow();
        user.getRole().setName(role);
        return userRepository.save(user);
    }

    public User blockUser(String id, boolean blocked) {
        User user = userRepository.findById(id).orElseThrow();
        user.setStatus(blocked ? User.Status.PASSIVE : User.Status.ACTIVE);
        return userRepository.save(user);
    }

    public List<User> searchUsers(String query, String role, User.Status status, String sort) {
        List<User> users;
        if (query != null && !query.isEmpty()) {
            users = userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
        } else if (role != null && !role.isEmpty()) {
            users = userRepository.findByRole_Name(role);
        } else if (status != null) {
            users = userRepository.findByStatus(status);
        } else {
            users = userRepository.findAll();
        }
        users.sort((u1, u2) -> {
            switch (sort) {
                case "name":
                    return u1.getName().compareToIgnoreCase(u2.getName());
                case "lastLogin":
                    if (u1.getLastLogin() == null && u2.getLastLogin() == null) return 0;
                    if (u1.getLastLogin() == null) return 1;
                    if (u2.getLastLogin() == null) return -1;
                    return u2.getLastLogin().compareTo(u1.getLastLogin());
                case "role":
                    return u1.getRole().getName().compareToIgnoreCase(u2.getRole().getName());
                default:
                    return 0;
            }
        });
        return users;
    }

    public List<HiveResponse> getUserHives(String userId) {
        return hiveService.getAllHivesByUserId(userId);
    }

    public HiveResponse addHiveToUser(String userId, HiveCreateRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        return hiveService.createHive(request, user);
    }

    public HiveResponse updateUserHive(String userId, Long hiveId, HiveUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        return hiveService.updateHiveAsAdmin(hiveId, request, user);
    }

    public void deleteUserHive(String userId, Long hiveId) {
        hiveService.deleteHive(hiveId);
    }

    public HiveSensorData addSensorDataToUserHive(String userId, Long hiveId, HiveSensorData data) {
        return hiveService.addSensorData(hiveId, data);
    }
}
