package com.example.controller;

import com.example.dao.DepartementDAO;
import com.example.dao.EmployeDAO;
import com.example.dao.ProjetDAO;
import com.example.model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.*;

@WebServlet("/projet")
public class ProjetServlet extends HttpServlet {
    private final ProjetDAO projetDAO = new ProjetDAO();
    private final EmployeDAO employeDAO = new EmployeDAO();
    private final DepartementDAO departementDAO = new DepartementDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
        if (user == null) { response.sendRedirect("login.jsp"); return; }

        NomRole role = user.getRole().getNomRole();
        String action = Optional.ofNullable(request.getParameter("action")).orElse("list");

        boolean isEmploye = (role == NomRole.EMPLOYE);
        boolean isChefProjet = (role == NomRole.CHEF_DE_PROJET);
        boolean isAdminOrChefDep = (role == NomRole.ADMINISTRATEUR || role == NomRole.CHEF_DE_DEPARTEMENT);

        if ((isEmploye || isChefProjet) && ("add".equals(action) || "delete".equals(action))) {
            response.sendRedirect("projet?action=list");
            return;
        }

        switch (action) {
            case "add": {
                if (!isAdminOrChefDep) { response.sendRedirect("projet?action=list"); return; }
                request.setAttribute("employes", employeDAO.getAll());
                request.setAttribute("departements", departementDAO.getAll());
                request.getRequestDispatcher("jsp/projets-form.jsp").forward(request, response);
                break;
            }
            case "edit": {
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Projet projet = projetDAO.getById(idEdit);
                if (projet == null) { response.sendRedirect("projet?action=list"); return; }

                if (isChefProjet) {
                    int me = (int) user.getEmploye().getId();
                    if (projet.getChefProjet() == null || projet.getChefProjet().getId() != me) {
                        response.sendRedirect("projet?action=list");
                        return;
                    }
                }
                if (isEmploye) { response.sendRedirect("projet?action=list"); return; }

                request.setAttribute("projet", projet);
                request.setAttribute("employes", employeDAO.getAll());
                request.setAttribute("departements", departementDAO.getAll());
                request.getRequestDispatcher("jsp/projets-form.jsp").forward(request, response);
                break;
            }
            case "delete": {
                if (!isAdminOrChefDep) { response.sendRedirect("projet?action=list"); return; }
                int idDel = Integer.parseInt(request.getParameter("id"));
                projetDAO.delete(idDel);
                response.sendRedirect("projet?action=list");
                break;
            }
            default: {
                List<Projet> list;
                if (isEmploye) {
                    list = projetDAO.getByEmploye((int) user.getEmploye().getId());
                } else if (isChefProjet) {
                    list = projetDAO.getByChefProjet((int) user.getEmploye().getId());
                } else if (role == NomRole.CHEF_DE_DEPARTEMENT) {
                    int deptId = Math.toIntExact(user.getEmploye().getDepartement().getId());
                    list = projetDAO.getByDepartement(deptId);
                } else if (role == NomRole.ADMINISTRATEUR) {
                    list = projetDAO.getAll();
                }
                else {
                    list = Collections.emptyList();
                }

                request.setAttribute("projets", list);
                request.getRequestDispatcher("jsp/projets.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
        if (user == null) { response.sendRedirect("login.jsp"); return; }

        NomRole role = user.getRole().getNomRole();
        boolean isEmploye = (role == NomRole.EMPLOYE);
        boolean isChefProjet = (role == NomRole.CHEF_DE_PROJET);
        boolean isAdminOrChefDep = (role == NomRole.ADMINISTRATEUR || role == NomRole.CHEF_DE_DEPARTEMENT);

        String idStr = request.getParameter("id");
        String nom = request.getParameter("nom");
        String depStr = request.getParameter("depId");
        String description = request.getParameter("description");
        String etatStr = request.getParameter("etat");
        String budgetStr = request.getParameter("budget");
        String dateDebutStr = request.getParameter("dateDebut");
        String dateFinStr = request.getParameter("dateFin");
        String chefIdStr = request.getParameter("chefId"); // <--- IMPORTANT (nom aligné au form)
        String[] employesIds = request.getParameterValues("employesAssignes");

        Projet projet;

        if (idStr != null && !idStr.isEmpty()) {
            // UPDATE
            int id = Integer.parseInt(idStr);
            projet = projetDAO.getById(id);
            if (projet == null) { response.sendRedirect("projet?action=list"); return; }

            if (isEmploye) { response.sendRedirect("projet?action=list"); return; }
            if (isChefProjet) {
                int me = (int) user.getEmploye().getId();
                if (projet.getChefProjet() == null || projet.getChefProjet().getId() != me) {
                    response.sendRedirect("projet?action=list");
                    return;
                }
            }
        } else {
            // CREATE
            if (!isAdminOrChefDep) { response.sendRedirect("projet?action=list"); return; }
            projet = new Projet();
        }

        // Hydratation de base
        projet.setNom(nom);
        if (depStr != null && !depStr.isEmpty()) projet.setDepartement(departementDAO.getById(Integer.parseInt(depStr)));
        projet.setDescription(description);
        if (etatStr != null && !etatStr.isEmpty()) projet.setEtat(EtatProjet.valueOf(etatStr));
        if (budgetStr != null && !budgetStr.isEmpty()) projet.setBudget(Double.parseDouble(budgetStr));
        if (dateDebutStr != null && !dateDebutStr.isEmpty()) projet.setDateDebut(Date.valueOf(dateDebutStr));
        if (dateFinStr != null && !dateFinStr.isEmpty()) projet.setDateFin(Date.valueOf(dateFinStr));

        // Chef de projet
        if (isAdminOrChefDep) {
            if (chefIdStr != null && !chefIdStr.isEmpty()) {
                Employe chef = employeDAO.getById(Integer.parseInt(chefIdStr));
                projet.setChefProjet(chef); // DAO l'attachera
            } else {
                projet.setChefProjet(null);
            }
        } else if (isChefProjet) {
            projet.setChefProjet(user.getEmploye()); // forcer à soi-même
        }

        // Employés affectés
        Set<Employe> employesAffectes = new HashSet<>();
        if (employesIds != null) {
            for (String empIdStr : employesIds) {
                Employe emp = new Employe();
                emp.setId(Integer.parseInt(empIdStr));
                employesAffectes.add(emp);
            }
        }
        projet.setEmployes(employesAffectes);

        // Persistance
        if (idStr != null && !idStr.isEmpty()) {
            projetDAO.update(projet);
        } else {
            projetDAO.save(projet);
        }

        response.sendRedirect("projet?action=list");
    }
}
