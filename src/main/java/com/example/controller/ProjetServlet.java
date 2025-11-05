package com.example.controller;

import com.example.dao.ProjetDAO;
import com.example.model.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.dao.EmployeDAO;


@WebServlet("/projet")
public class ProjetServlet extends HttpServlet {
    private final ProjetDAO projetDAO = new ProjetDAO();
    private final EmployeDAO employeDAO = new EmployeDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (user.getRole().getNomRole() == NomRole.EMPLOYE) {
            // Les employés n’ont accès qu’à la liste
            String action = request.getParameter("action");
            if (action != null && (action.equals("add") || action.equals("edit") || action.equals("delete"))) {
                response.sendRedirect("projet?action=list");
                return;
            }
        }

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                request.setAttribute("employes", employeDAO.getAll());
                request.getRequestDispatcher("jsp/projets-form.jsp").forward(request, response);
                break;
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Projet projet = projetDAO.getById(idEdit);

                if (projet != null) {
                    request.setAttribute("projet", projet);
                    request.setAttribute("employes", employeDAO.getAll());
                    request.getRequestDispatcher("jsp/projets-form.jsp").forward(request, response);
                } else {
                    response.sendRedirect("projet?action=list");
                }
                break;
            case "delete":
                int idDel = Integer.parseInt(request.getParameter("id"));
                projetDAO.delete(idDel);
                response.sendRedirect("projet?action=list");
                break;
            default:
                List<Projet> list = new ArrayList<>();
                System.out.println("🔎 Rôle connecté : " + user.getRole().getNomRole());

                // Si ce n'est pas un admin ou le chef de département, il ne voit que ses projets
                if (user.getRole().getNomRole() == NomRole.EMPLOYE) {
                    List <Projet> projets = projetDAO.getByEmploye((int) user.getEmploye().getId());
                    if (projets != null) list.addAll(projets);
                }

                // Sinon → il voit tout
                else if (user.getRole().getNomRole() == NomRole.ADMINISTRATEUR || user.getRole().getNomRole() == NomRole.CHEF_DE_DEPARTEMENT || user.getRole().getNomRole() == NomRole.CHEF_DE_PROJET) {
                    list = projetDAO.getAll();
                }

                else {
                    System.out.println("Role non vérifié !!!!");
                }

                request.setAttribute("projets", list);
                request.getRequestDispatcher("jsp/projets.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        String etatStr = request.getParameter("etat");
        String budgetStr = request.getParameter("budget");
        String dateDebutStr = request.getParameter("dateDebut");
        String dateFinStr = request.getParameter("dateFin");
        String[] employesIds = request.getParameterValues("employesAssignes");

        Projet projet;

        if (idStr != null && !idStr.isEmpty()) {
            // --- UPDATE ---
            int id = Integer.parseInt(idStr);
            projet = projetDAO.getById(id); // chargé avec ses employés
        } else {
            // --- CREATE ---
            projet = new Projet();
        }

        // --- Remplir les infos du projet ---
        projet.setNom(nom);
        projet.setDescription(description);

        if (etatStr != null && !etatStr.isEmpty())
            projet.setEtat(EtatProjet.valueOf(etatStr));

        if (budgetStr != null && !budgetStr.isEmpty())
            projet.setBudget(Double.parseDouble(budgetStr));

        if (dateDebutStr != null && !dateDebutStr.isEmpty())
            projet.setDateDebut(Date.valueOf(dateDebutStr));

        if (dateFinStr != null && !dateFinStr.isEmpty())
            projet.setDateFin(Date.valueOf(dateFinStr));

        // --- Associer les employés sélectionnés ---
        Set<Employe> employesAffectes = new HashSet<>();
        if (employesIds != null) {
            for (String empIdStr : employesIds) {
                Employe emp = new Employe();
                emp.setId(Integer.parseInt(empIdStr));
                employesAffectes.add(emp);
            }
        }
        projet.setEmployes(employesAffectes);

        // --- Persister ---
        if (idStr != null && !idStr.isEmpty()) {
            projetDAO.update(projet);
        } else {
            projetDAO.save(projet);
        }

        response.sendRedirect("projet?action=list");
    }
}