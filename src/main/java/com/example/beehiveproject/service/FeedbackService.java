package com.example.beehiveproject.service;

import com.example.beehiveproject.entity.Feedback;
import com.example.beehiveproject.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback saveFeedback(Feedback feedback) {
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setStatus("NEW");
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Feedback replyFeedback(Long id, String reply) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow();
        feedback.setReply(reply);
        feedback.setStatus("REPLIED");
        return feedbackRepository.save(feedback);
    }

    public Feedback updateStatus(Long id, String status) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow();
        feedback.setStatus(status);
        return feedbackRepository.save(feedback);
    }
} 