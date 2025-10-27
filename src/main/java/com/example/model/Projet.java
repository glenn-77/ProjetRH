package com.example.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * Entité Projet
 * Représente un projet dans le système de gestion RH.
 * Chaque projet peut être lié à plusieurs employés (relation ManyToMany).
 */
@Entity
@Table(name = "projet")
public class Projet {

    // ----- ATTRIBUTS -----

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nom;

    // Brève description du projet
    @Column(columnDefinition = "TEXT")
    private String description;

    // État du projet (ex: En cours, Terminé, En attente)
    @Enumerated(EnumType.STRING)
    private EtatProjet etat;

    // Budget estimé ou alloué au projet
    @Column(nullable = false)
    private double budget;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    // Liste des employés associés à ce projet
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employe_projet",
            joinColumns = @JoinColumn(name = "projet_id"),
            inverseJoinColumns = @JoinColumn(name = "employe_id")
    )    private Set<Employe> employes;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employe chefProjet;

    // ----- CONSTRUCTEURS -----

    public Projet() {
    }

    public Projet(String nom, String description, double budget, Date dateDebut, Date dateFin) {
        this.nom = nom;
        this.description = description;
        this.etat = EtatProjet.EN_COURS;
        this.budget = budget;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // ----- GETTERS & SETTERS -----

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EtatProjet getEtat() {
        return etat;
    }

    public void setEtat(EtatProjet etat) {
        this.etat = etat;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Set<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(Set<Employe> employes) {
        this.employes = employes;
    }

    public Employe getChefProjet() {
        return chefProjet;
    }

    public void setChefProjet(Employe chefProjet) {
        this.chefProjet = chefProjet;
    }

    // ----- MÉTHODES UTILES -----

    /**
     * Indique si le projet est actuellement en cours
     * en fonction de la date du jour et des dates de début/fin.
     */
    public boolean isEnCours() {
        if (dateDebut == null || dateFin == null) return false;
        Date now = new Date();
        return now.after(dateDebut) && now.before(dateFin);
    }

    @Override
    public String toString() {
        return "Projet{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", etat='" + etat + '\'' +
                ", budget=" + budget +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Projet)) return false;
        Projet projet = (Projet) o;
        return id == projet.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addEmploye(Employe e) {
        employes.add(e);
        e.getProjets().add(this);
    }

    public void removeEmploye(Employe e) {
        employes.remove(e);
        e.getProjets().remove(this);
    }
}