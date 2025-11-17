package com.example.dto;

public class EmployeDTO {
    public int id;
    public String nom;
    public String prenom;
    public String poste;

    public EmployeDTO(int id, String nom, String prenom, String poste) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
    }
}

