package com.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private EtatProjet etat = EtatProjet.EN_COURS;

    @Column(nullable = false)
    private double budget;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    // 🔹 Relation ManyToMany avec les employés
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employe_projet",
            joinColumns = @JoinColumn(name = "projet_id"),
            inverseJoinColumns = @JoinColumn(name = "employe_id")
    )
    @JsonIgnore
    private Set<Employe> employes = new HashSet<>();

    // 🔹 Chef de projet
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employe chefProjet;

    // 🔹 Département du projet
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id")
    private Departement departement;

    // ----- MÉTHODES UTILES -----

    public boolean isEnCours() {
        if (dateDebut == null || dateFin == null) return false;
        LocalDate now = LocalDate.now();
        return now.isAfter(dateDebut) && now.isBefore(dateFin);
    }

    public void addEmploye(Employe e) {
        employes.add(e);
        e.getProjets().add(this);
    }

    public void removeEmploye(Employe e) {
        employes.remove(e);
        e.getProjets().remove(this);
    }

    @Override
    public String toString() {
        return nom + " (" + etat + ")";
    }
}
