package com.example.beehiveproject.dto.request;

import java.time.LocalDateTime;

public class HiveUpdateRequest {
    private String name;
    private String konum;
    private String durum;
    private Double konumLat;
    private Double konumLng;
    private Long koloniId;
    private String aciklama;
    private LocalDateTime installDate;
    // Getter ve Setterlar
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getKonum() { return konum; }
    public void setKonum(String konum) { this.konum = konum; }
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
    public Double getKonumLat() { return konumLat; }
    public void setKonumLat(Double konumLat) { this.konumLat = konumLat; }
    public Double getKonumLng() { return konumLng; }
    public void setKonumLng(Double konumLng) { this.konumLng = konumLng; }
    public Long getKoloniId() { return koloniId; }
    public void setKoloniId(Long koloniId) { this.koloniId = koloniId; }
    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
    public LocalDateTime getInstallDate() { return installDate; }
    public void setInstallDate(LocalDateTime installDate) { this.installDate = installDate; }
}