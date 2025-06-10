package com.example.beehiveproject.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Hive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isInfected;
    private boolean needsMaintenance;

    private double yearlyHoneyProductionKg;

    private double latitude;
    private double longitude;

    private LocalDate lastUpdated;

    private String name;
    private String konum;
    private String durum;

    private String ad;
    private Double konumLat;
    private Double konumLng;
    private String aciklama;
    private java.time.LocalDateTime olusturmaTarihi;

    private Integer estimatedBeeCount;

    private LocalDateTime installDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "koloni_id")
    private Colony colony;

    @ManyToOne
    @JoinColumn(name = "parent_hive_id")
    private Hive parentHive;

    @ElementCollection
    private List<String> mergedFromNames;

    @ElementCollection
    private List<Long> mergedFromIds;

    @OneToMany(mappedBy = "hive", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<HiveSensorData> sensorDataList;

    @OneToMany(mappedBy = "hive", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HiveEvent> hiveEvents;

    @OneToMany(mappedBy = "hive", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HiveAlert> hiveAlerts;

    @OneToMany(mappedBy = "kovan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BakimGorevi> bakimGorevleri;

    @OneToMany(mappedBy = "hive", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sensor> sensors;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public boolean isInfected() { return isInfected; }
    public void setInfected(boolean infected) { isInfected = infected; }
    public boolean isNeedsMaintenance() { return needsMaintenance; }
    public void setNeedsMaintenance(boolean needsMaintenance) { this.needsMaintenance = needsMaintenance; }
    public double getYearlyHoneyProductionKg() { return yearlyHoneyProductionKg; }
    public void setYearlyHoneyProductionKg(double yearlyHoneyProductionKg) { this.yearlyHoneyProductionKg = yearlyHoneyProductionKg; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public LocalDate getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDate lastUpdated) { this.lastUpdated = lastUpdated; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getKonum() { return konum; }
    public void setKonum(String konum) { this.konum = konum; }
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public Double getKonumLat() { return konumLat; }
    public void setKonumLat(Double konumLat) { this.konumLat = konumLat; }
    public Double getKonumLng() { return konumLng; }
    public void setKonumLng(Double konumLng) { this.konumLng = konumLng; }
    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
    public java.time.LocalDateTime getOlusturmaTarihi() { return olusturmaTarihi; }
    public void setOlusturmaTarihi(java.time.LocalDateTime olusturmaTarihi) { this.olusturmaTarihi = olusturmaTarihi; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Colony getColony() { return colony; }
    public void setColony(Colony colony) { this.colony = colony; }
    public Hive getParentHive() { return parentHive; }
    public void setParentHive(Hive parentHive) { this.parentHive = parentHive; }
    public List<String> getMergedFromNames() { return mergedFromNames; }
    public void setMergedFromNames(List<String> mergedFromNames) { this.mergedFromNames = mergedFromNames; }
    public List<Long> getMergedFromIds() { return mergedFromIds; }
    public void setMergedFromIds(List<Long> mergedFromIds) { this.mergedFromIds = mergedFromIds; }
    public List<HiveSensorData> getSensorDataList() { return sensorDataList; }
    public void setSensorDataList(List<HiveSensorData> sensorDataList) { this.sensorDataList = sensorDataList; }
    public List<HiveEvent> getHiveEvents() { return hiveEvents; }
    public void setHiveEvents(List<HiveEvent> hiveEvents) { this.hiveEvents = hiveEvents; }
    public List<HiveAlert> getHiveAlerts() { return hiveAlerts; }
    public void setHiveAlerts(List<HiveAlert> hiveAlerts) { this.hiveAlerts = hiveAlerts; }
    public List<BakimGorevi> getBakimGorevleri() { return bakimGorevleri; }
    public void setBakimGorevleri(List<BakimGorevi> bakimGorevleri) { this.bakimGorevleri = bakimGorevleri; }
    public List<Sensor> getSensors() { return sensors; }
    public void setSensors(List<Sensor> sensors) { this.sensors = sensors; }
    public Integer getEstimatedBeeCount() { return estimatedBeeCount; }
    public void setEstimatedBeeCount(Integer estimatedBeeCount) { this.estimatedBeeCount = estimatedBeeCount; }
    public LocalDateTime getInstallDate() { return installDate; }
    public void setInstallDate(LocalDateTime installDate) { this.installDate = installDate; }
}