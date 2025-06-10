package com.example.beehiveproject.dto.response;

public class YearlyProductionDTO {
    private Integer year;
    private Double totalKg;

    public YearlyProductionDTO(Integer year, Double totalKg) {
        this.year = year;
        this.totalKg = totalKg;
    }

    public Integer getYear() { return year; }
    public Double getTotalKg() { return totalKg; }
} 