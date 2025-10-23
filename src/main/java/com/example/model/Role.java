package com.example.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private NomRole nomRole;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Utilisateur> users;

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public NomRole getNomRole() { return nomRole; }
    public void setNomRole(NomRole nomRole) { this.nomRole = nomRole; }

    public List<Utilisateur> getUsers() { return users; }
    public void setUsers(List<Utilisateur> users) { this.users = users; }
}
