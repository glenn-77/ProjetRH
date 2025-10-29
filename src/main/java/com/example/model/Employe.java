package com.example.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Classe Entité Employe
 * Représente un employé du service RH.
 * Reliée à un département (ManyToOne) et à plusieurs projets (ManyToMany).
 */
@Entity
@Table(name = "employe")
public class Employe {

    // ======= ATTRIBUTS =======

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String matricule;

    private String poste;

    @Enumerated(EnumType.STRING)
    private Grade grade = Grade.JUNIOR;
    private String adresse;
    private String telephone;

    @Column(nullable = false)
    private double salaireBase;

    private LocalDate dateEmbauche;

    @Column(unique = true)
    private String email; // Champ pour le contact de l'employé

    // Relation avec le département
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departement_id")
    private Departement departement;

    // Relation avec les projets
    @ManyToMany(mappedBy = "employes", fetch = FetchType.LAZY)
    private Set<Projet> projets = new HashSet<>();

    @OneToMany(mappedBy = "employe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FicheDePaie> fichesPaie;
    // ======= CONSTRUCTEURS =======

    public Employe() {
    }

    public Employe(String nom, String prenom, String matricule, String poste,
                   double salaireBase, String addresse, String telephone, String email, Departement departement) {
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.poste = poste;
        this.salaireBase = salaireBase;
        this.email = email;
        this.setAdresse(addresse);
        this.setTelephone(telephone);
        this.departement = departement;
    }

    // ======= GETTERS & SETTERS =======

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getPoste() { return poste; }
    public void setPoste(String poste) { this.poste = poste; }

    public Grade getGrade() { return grade; }
    public void setGrade(Grade grade) { this.grade = grade; }

    public Double getSalaireBase() { return salaireBase; }
    public void setSalaireBase(double salaireBase) { this.salaireBase = salaireBase; }

    public Departement getDepartement() { return departement; }
    public void setDepartement(Departement departement) { this.departement = departement; }

    public Set<Projet> getProjets() { return projets; }
    public void setProjets(Set<Projet> projets) { this.projets = projets; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public Set<FicheDePaie> getFichesPaie() {
        return fichesPaie;
    }

    public void setFichesPaie(Set<FicheDePaie> fichesPaie) {
        this.fichesPaie = fichesPaie;
    }

    // ======= MÉTHODES UTILITAIRES =======

    public void addProjet(Projet p) {
        projets.add(p);
        p.getEmployes().add(this);
    }

    public void removeProjet(Projet p) {
        projets.remove(p);
        p.getEmployes().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employe)) return false;
        Employe employe = (Employe) o;
        return id == employe.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    /**
     * Renvoie une description textuelle simple (utile pour le debug ou les logs)
     */
    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", poste='" + poste + '\'' +
                ", grade='" + grade + '\'' +
                ", salaire=" + getSalaireBase() +
                ", departement=" + (departement != null ? departement.getNom() : "Aucun") +
                '}';
    }
}
