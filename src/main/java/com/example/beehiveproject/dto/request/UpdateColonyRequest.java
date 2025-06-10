package com.example.beehiveproject.dto.request;

import java.time.LocalDateTime;

public class UpdateColonyRequest {
    private String ad;
    private String anaAriTuru;
    private LocalDateTime olusturmaTarihi;
    private LocalDateTime bolunmeTarihi;
    private String saglikDurumu;
    private String durum;
    private String notlar;
    private Long kaynakKoloniId;

    public UpdateColonyRequest() {}

    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public String getAnaAriTuru() { return anaAriTuru; }
    public void setAnaAriTuru(String anaAriTuru) { this.anaAriTuru = anaAriTuru; }
    public LocalDateTime getOlusturmaTarihi() { return olusturmaTarihi; }
    public void setOlusturmaTarihi(LocalDateTime olusturmaTarihi) { this.olusturmaTarihi = olusturmaTarihi; }
    public LocalDateTime getBolunmeTarihi() { return bolunmeTarihi; }
    public void setBolunmeTarihi(LocalDateTime bolunmeTarihi) { this.bolunmeTarihi = bolunmeTarihi; }
    public String getSaglikDurumu() { return saglikDurumu; }
    public void setSaglikDurumu(String saglikDurumu) { this.saglikDurumu = saglikDurumu; }
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
    public String getNotlar() { return notlar; }
    public void setNotlar(String notlar) { this.notlar = notlar; }
    public Long getKaynakKoloniId() { return kaynakKoloniId; }
    public void setKaynakKoloniId(Long kaynakKoloniId) { this.kaynakKoloniId = kaynakKoloniId; }
} 