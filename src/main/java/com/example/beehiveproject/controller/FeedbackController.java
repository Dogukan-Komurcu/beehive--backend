package com.example.beehiveproject.controller;

import com.example.beehiveproject.entity.Feedback;
import com.example.beehiveproject.service.FeedbackService;
import com.example.beehiveproject.repository.UserRepository;
import com.example.beehiveproject.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    // Kullanıcı mesajı gönderir
    @PostMapping
    public Feedback sendFeedback(@RequestBody Feedback feedback, @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            String userId = jwtUtil.extractId(token);
            feedback.setUser(userRepository.findById(userId).orElse(null));
        }
        return feedbackService.saveFeedback(feedback);
    }

    // Admin: Tüm mesajları listele
    @GetMapping
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    // Admin: Mesaja yanıt ver
    @PutMapping("/{id}/reply")
    public Feedback replyFeedback(@PathVariable Long id, @RequestBody String reply) {
        return feedbackService.replyFeedback(id, reply);
    }

    // Admin: Mesaj durumunu güncelle
    @PutMapping("/{id}/status")
    public Feedback updateStatus(@PathVariable Long id, @RequestParam String status) {
        return feedbackService.updateStatus(id, status);
    }
} 