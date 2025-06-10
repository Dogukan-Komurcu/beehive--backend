package com.example.beehiveproject.repository;

import com.example.beehiveproject.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
} 