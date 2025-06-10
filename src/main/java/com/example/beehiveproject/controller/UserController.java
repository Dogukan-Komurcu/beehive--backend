package com.example.beehiveproject.controller;

import com.example.beehiveproject.entity.User;
import com.example.beehiveproject.repository.UserRepository;
import com.example.beehiveproject.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // Kullanıcı kendi bilgilerini getir
    @GetMapping("/me")
    public User getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        return userRepository.findById(userId).orElseThrow();
    }

    // Kullanıcı kendi bilgilerini günceller
    @PutMapping("/me")
    public User updateCurrentUser(@RequestHeader("Authorization") String authHeader, @RequestBody User updated) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        User user = userRepository.findById(userId).orElseThrow();
        user.setName(updated.getName());
        user.setFullName(updated.getFullName());
        user.setEmail(updated.getEmail());
        return userRepository.save(user);
    }

    // Kullanıcı kendi kaydını siler
    @DeleteMapping("/me")
    public void deleteCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractId(token);
        userRepository.deleteById(userId);
    }
}