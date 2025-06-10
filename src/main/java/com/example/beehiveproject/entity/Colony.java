package com.example.beehiveproject.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.example.beehiveproject.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "koloniler")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Colony {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long koloniId;

    private String ad;
    private String anaAriTuru;
    private LocalDateTime olusturmaTarihi;
    private LocalDateTime bolunmeTarihi;
    private String saglikDurumu;
    private String durum; // aktif/pasif
    private String notlar;

    @ManyToOne
    @JoinColumn(name = "kaynak_koloni_id")
    private Colony kaynakKoloni;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Getter ve Setterlar
    public Long getKoloniId() {
        return koloniId;
    }
    public void setKoloniId(Long koloniId) {
        this.koloniId = koloniId;
    }
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
    public Colony getKaynakKoloni() { return kaynakKoloni; }
    public void setKaynakKoloni(Colony kaynakKoloni) { this.kaynakKoloni = kaynakKoloni; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
} 