package com.example.beehiveproject.entity;

import jakarta.persistence.*;

@Entity
public class BeeAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year; // 2025, 2026, ...
    private int month; // 1-12
    private int startDay; // Başlangıç günü
    private int endDay;   // Bitiş günü
    private String title; // Başlık
    private String icon;  // Emoji veya ikon
    private String type;  // uyarı, tavsiye, not, dikkat, aksiyon, öneri, eylem, ek, alternatif, yöntem
    @Column(length = 1000)
    private String message; // Açıklama

    // Getter ve Setter'lar
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }
    public int getStartDay() { return startDay; }
    public void setStartDay(int startDay) { this.startDay = startDay; }
    public int getEndDay() { return endDay; }
    public void setEndDay(int endDay) { this.endDay = endDay; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
} 