package com.example.beehiveproject.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class HiveResponse {
    private Long id;
    private String name;
    private String konum;
    private String durum;
    private Double sicaklik;
    private Double nem;
    private Double batarya;
    private String sonVeri;
    private Long koloniId;
    private String aciklama;
    private LocalDateTime olusturmaTarihi;
    private String kullaniciId;
    private Long bolunenKovanId;
    private String bolunenKovanAdi;
    private List<String> mergedFromNames;
    private List<Long> mergedFromIds;
    private Integer estimatedBeeCount;
    private LocalDateTime installDate;
    // Getter ve Setterlar
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getKonum() { return konum; }
    public void setKonum(String konum) { this.konum = konum; }
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
    public Double getSicaklik() { return sicaklik; }
    public void setSicaklik(Double sicaklik) { this.sicaklik = sicaklik; }
    public Double getNem() { return nem; }
    public void setNem(Double nem) { this.nem = nem; }
    public Double getBatarya() { return batarya; }
    public void setBatarya(Double batarya) { this.batarya = batarya; }
    public String getSonVeri() { return sonVeri; }
    public void setSonVeri(String sonVeri) { this.sonVeri = sonVeri; }
    public Long getKoloniId() { return koloniId; }
    public void setKoloniId(Long koloniId) { this.koloniId = koloniId; }
    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
    public LocalDateTime getOlusturmaTarihi() { return olusturmaTarihi; }
    public void setOlusturmaTarihi(LocalDateTime olusturmaTarihi) { this.olusturmaTarihi = olusturmaTarihi; }
    public String getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(String kullaniciId) { this.kullaniciId = kullaniciId; }
    public Long getBolunenKovanId() { return bolunenKovanId; }
    public void setBolunenKovanId(Long bolunenKovanId) { this.bolunenKovanId = bolunenKovanId; }
    public String getBolunenKovanAdi() { return bolunenKovanAdi; }
    public void setBolunenKovanAdi(String bolunenKovanAdi) { this.bolunenKovanAdi = bolunenKovanAdi; }
    public List<String> getMergedFromNames() { return mergedFromNames; }
    public void setMergedFromNames(List<String> mergedFromNames) { this.mergedFromNames = mergedFromNames; }
    public List<Long> getMergedFromIds() { return mergedFromIds; }
    public void setMergedFromIds(List<Long> mergedFromIds) { this.mergedFromIds = mergedFromIds; }
    public Integer getEstimatedBeeCount() { return estimatedBeeCount; }
    public void setEstimatedBeeCount(Integer estimatedBeeCount) { this.estimatedBeeCount = estimatedBeeCount; }
    public LocalDateTime getInstallDate() { return installDate; }
    public void setInstallDate(LocalDateTime installDate) { this.installDate = installDate; }
}