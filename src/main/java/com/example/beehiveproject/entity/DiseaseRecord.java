package com.example.beehiveproject.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class DiseaseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diseaseName;
    private String description;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colony_id")
    private Colony colony;

    @ManyToMany
    @JoinTable(
        name = "disease_record_hives",
        joinColumns = @JoinColumn(name = "disease_record_id"),
        inverseJoinColumns = @JoinColumn(name = "hive_id")
    )
    private List<Hive> hives;

    // Getter ve Setterlar
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDiseaseName() { return diseaseName; }
    public void setDiseaseName(String diseaseName) { this.diseaseName = diseaseName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Colony getColony() { return colony; }
    public void setColony(Colony colony) { this.colony = colony; }
    public List<Hive> getHives() { return hives; }
    public void setHives(List<Hive> hives) { this.hives = hives; }
} 