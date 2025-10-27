package com.example.controller;

import com.example.dao.ProjetDAO;
import com.example.model.EtatProjet;
import com.example.model.Projet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.dao.EmployeDAO;
import com.example.model.Employe;


@WebServlet("/projet")
public class ProjetServlet extends HttpServlet {
    private final ProjetDAO projetDAO = new ProjetDAO();
    private final EmployeDAO employeDAO = new EmployeDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                List<Projet> list = projetDAO.getAll();
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