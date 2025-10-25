package com.example.controller;

import com.example.dao.EmployeDAO;
import com.example.dao.DepartementDAO;
import com.example.model.Employe;
import com.example.model.Departement;

import com.example.model.Grade;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/employe")
public class EmployeServlet extends HttpServlet {

    private final EmployeDAO employeDAO = new EmployeDAO();
    private final DepartementDAO departementDAO = new DepartementDAO();

    /**
     * Gère les actions GET : liste, ajout, édition, suppression
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list"; // Par défaut : afficher la liste

        switch (action) {
            case "add": // --- AFFICHER FORMULAIRE AJOUT ---
                request.setAttribute("departements", departementDAO.getAll());
                RequestDispatcher addDispatcher = request.getRequestDispatcher("jsp/employes-form.jsp");
                addDispatcher.forward(request, response);
                break;

            case "edit": // --- AFFICHER FORMULAIRE MODIF ---
                try {
                    int idEdit = Integer.parseInt(request.getParameter("id"));
                    Employe emp = employeDAO.getById(idEdit);
                    request.setAttribute("employe", emp);
                    request.setAttribute("departements", departementDAO.getAll());
                    RequestDispatcher editDispatcher = request.getRequestDispatcher("jsp/employes-form.jsp");
                    editDispatcher.forward(request, response);
                } catch (NumberFormatException e) {
                    response.sendRedirect("employe?action=list");
                }
                break;

            case "listByGrade":
                List<Employe> byGrade = employeDAO.getAllOrderByGrade();
                request.setAttribute("employes", byGrade);
                RequestDispatcher gradeDispatcher = request.getRequestDispatcher("jsp/employes.jsp");
                gradeDispatcher.forward(request, response);
                break;

            case "listByPoste":
                List<Employe> byPoste = employeDAO.getAllOrderByPoste();
                request.setAttribute("employes", byPoste);
                RequestDispatcher posteDispatcher = request.getRequestDispatcher("jsp/employes.jsp");
                posteDispatcher.forward(request, response);
                break;

            case "delete": // --- SUPPRIMER EMPLOYÉ ---
                try {
                    int idDel = Integer.parseInt(request.getParameter("id"));
                    employeDAO.delete(idDel);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                response.sendRedirect("employe?action=list");
                break;

            default: // --- AFFICHER LISTE DES EMPLOYÉS ---
                List<Employe> list = employeDAO.getAll();
                request.setAttribute("employes", list);
                RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/employes.jsp");
                dispatcher.forward(request, response);
        }
    }

    private String generateMatricule() {
        String year = String.valueOf(java.time.Year.now().getValue()).substring(2); // ex: "25"
        char letter = (char) ('A' + (int)(Math.random() * 26)); // A à Z
        int num = (int)(Math.random() * 9000) + 1000;
        return "EMP" + year + letter + num;
    }


    /**
     * Gère la soumission du formulaire (ajout ou modification)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        //  Récupération sécurisée des paramètres
        String idStr = request.getParameter("id");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String poste = request.getParameter("poste");
        String grade = request.getParameter("grade");
        String adresse = request.getParameter("adresse");
        String telephone = request.getParameter("telephone");
        String salaireStr = request.getParameter("salaireBase");
        String depIdStr = request.getParameter("departementId");



        //  Conversion sûre des nombres
        double salaireBase = parseDoubleSafe(salaireStr);

        int deptId = parseIntSafe(depIdStr);

        // Création de l'objet Employe
        Employe e = new Employe();
        e.setNom(nom);
        e.setPrenom(prenom);
        e.setEmail(email);
        e.setAdresse(adresse);
        e.setTelephone(telephone);
        e.setPoste(poste);
        if (grade != null && !grade.isEmpty()) {
            e.setGrade(Grade.valueOf(grade));
        }
        e.setSalaireBase(salaireBase);

        if (deptId > 0) {
            Departement d = new Departement();
            d.setId(deptId);
            e.setDepartement(d);
        }

        //  Si un ID existe → mise à jour, sinon → création
        if (idStr != null && !idStr.trim().isEmpty()) {
            int id = Integer.parseInt(idStr);
            Employe existing = employeDAO.getById(id);
            if (existing != null) {
                existing.setNom(nom);
                existing.setPrenom(prenom);
                existing.setEmail(email);
                existing.setAdresse(adresse);
                existing.setTelephone(telephone);
                existing.setPoste(poste);
                if (grade != null && !grade.isEmpty()) {
                    existing.setGrade(Grade.valueOf(grade));
                }
                existing.setSalaireBase(salaireBase);

                if (deptId > 0) {
                    Departement d = new Departement();
                    d.setId(deptId);
                    existing.setDepartement(d);
                }

                employeDAO.update(existing);
            }
        } else {
            e.setMatricule(generateMatricule());
            e.setDateEmbauche(LocalDate.now());
            employeDAO.save(e);
        }


        //  Redirection vers la liste après l’ajout ou la mise à jour
        response.sendRedirect("employe?action=list");
    }

    /**
     * Méthode utilitaire pour convertir en double sans planter
     */
    private double parseDoubleSafe(String value) {
        if (value == null || value.trim().isEmpty()) return 0.0;
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Méthode utilitaire pour convertir en int sans erreur
     */
    private int parseIntSafe(String value) {
        if (value == null || value.trim().isEmpty()) return 0;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
