package com.example.beehiveproject.dto.response;

import java.util.List;
import com.example.beehiveproject.entity.BakimGorevi;

public class DashboardResponse {
    private int totalHives;
    private int infectedHives;
    private int maintenanceNeeded;
    private double totalProductionKg;
    private List<LocationDTO> hiveLocations;
    private List<YearlyProductionDTO> yearlyProduction;
    private List<BakimGorevi> incompleteTasks;

    // Constructor
    public DashboardResponse(int totalHives, int infectedHives, int maintenanceNeeded, double totalProductionKg,
                             List<LocationDTO> hiveLocations, List<YearlyProductionDTO> yearlyProduction, List<BakimGorevi> incompleteTasks) {
        this.totalHives = totalHives;
        this.infectedHives = infectedHives;
        this.maintenanceNeeded = maintenanceNeeded;
        this.totalProductionKg = totalProductionKg;
        this.hiveLocations = hiveLocations;
        this.yearlyProduction = yearlyProduction;
        this.incompleteTasks = incompleteTasks;
    }

    // Getters
    public int getTotalHives() { return totalHives; }
    public int getInfectedHives() { return infectedHives; }
    public int getMaintenanceNeeded() { return maintenanceNeeded; }
    public double getTotalProductionKg() { return totalProductionKg; }
    public List<LocationDTO> getHiveLocations() { return hiveLocations; }
    public List<YearlyProductionDTO> getYearlyProduction() { return yearlyProduction; }
    public List<BakimGorevi> getIncompleteTasks() { return incompleteTasks; }
}