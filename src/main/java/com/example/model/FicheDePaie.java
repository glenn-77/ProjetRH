package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "fiche_de_paie")
public class FicheDePaie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int mois;
    private int annee;

    @Column(nullable = false)
    private Double salaireBase;

    private Double prime = 0.0;
    private Double deduction = 0.0;

    @Transient
    private Double netAPayer;

    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employe employe;

    private LocalDate date_generation;

    public FicheDePaie() {}

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public int getMois() { return mois; }
    public void setMois(int mois) { this.mois = mois; }

    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }

    public double getNetAPayer() {
        if (netAPayer == null)
            calculNetAPayer();
        return netAPayer;
    }

    @PrePersist
    public void calculNetAPayer() {
        this.netAPayer = this.getSalaireBase() + this.getPrime() - this.getDeduction();
    }

    public void setNetAPayer(double netAPayer) { this.netAPayer = netAPayer; }

    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }

    public Double getSalaireBase() {
        return salaireBase;
    }

    public void setSalaireBase(Double salaireBase) {
        this.salaireBase = salaireBase;
    }

    public Double getPrime() {
        return prime;
    }

    public void setPrime(Double prime) {
        this.prime = prime;
    }

    public Double getDeduction() {
        return deduction;
    }

    public void setDeduction(Double deduction) {
        this.deduction = deduction;
    }

    public LocalDate getDate_generation() {
        return date_generation;
    }

    public void setDate_generation(LocalDate date_generation) {
        this.date_generation = date_generation;
    }
}