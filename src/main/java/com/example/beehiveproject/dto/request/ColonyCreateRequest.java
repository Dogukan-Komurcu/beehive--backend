package com.example.beehiveproject.dto.request;

import java.time.LocalDateTime;

public class ColonyCreateRequest {
    private String ad;
    private String anaAriTuru;
    private LocalDateTime olusturmaTarihi;
    private String saglikDurumu;
    private String durum;
    private String notlar;
    private Long kaynakKoloniId;
    private String userId;
    // Getter ve Setterlar
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public String getAnaAriTuru() { return anaAriTuru; }
    public void setAnaAriTuru(String anaAriTuru) { this.anaAriTuru = anaAriTuru; }
    public LocalDateTime getOlusturmaTarihi() { return olusturmaTarihi; }
    public void setOlusturmaTarihi(LocalDateTime olusturmaTarihi) { this.olusturmaTarihi = olusturmaTarihi; }
    public String getSaglikDurumu() { return saglikDurumu; }
    public void setSaglikDurumu(String saglikDurumu) { this.saglikDurumu = saglikDurumu; }
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
    public String getNotlar() { return notlar; }
    public void setNotlar(String notlar) { this.notlar = notlar; }
    public Long getKaynakKoloniId() { return kaynakKoloniId; }
    public void setKaynakKoloniId(Long kaynakKoloniId) { this.kaynakKoloniId = kaynakKoloniId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
} 