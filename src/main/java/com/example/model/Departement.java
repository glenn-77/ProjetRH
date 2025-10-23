package com.example.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "departement")
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @OneToOne
    @JoinColumn(name = "chef_id")
    private Employe chef;

    @OneToMany(mappedBy = "departement")
    private Set<Employe> employes;

    public Departement() {}

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Set<Employe> getEmployes() { return employes; }
    public void setEmployes(Set<Employe> employes) { this.employes = employes; }

    public Employe getChef() {
        return chef;
    }

    public void setChef(Employe chef) {
        this.chef = chef;
    }
}