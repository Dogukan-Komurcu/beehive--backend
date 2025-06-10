package com.example.beehiveproject.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class BakimGorevi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String baslik;
    private String aciklama;
    private LocalDate tarih;
    private boolean tamamlandi;

    @ManyToOne
    @JoinColumn(name = "koloni_id")
    private Colony koloni;

    @ManyToOne
    @JoinColumn(name = "kovan_id")
    private Hive kovan;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBaslik() { return baslik; }
    public void setBaslik(String baslik) { this.baslik = baslik; }

    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }

    public LocalDate getTarih() { return tarih; }
    public void setTarih(LocalDate tarih) { this.tarih = tarih; }

    public boolean isTamamlandi() { return tamamlandi; }
    public void setTamamlandi(boolean tamamlandi) { this.tamamlandi = tamamlandi; }

    public Colony getKoloni() { return koloni; }
    public void setKoloni(Colony koloni) { this.koloni = koloni; }

    public Hive getKovan() { return kovan; }
    public void setKovan(Hive kovan) { this.kovan = kovan; }
} 