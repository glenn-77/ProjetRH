package com.example.controller;

import com.example.dao.FicheDePaieDAO;
import com.example.dao.EmployeDAO;
import com.example.model.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet("/fiche")
public class FicheDePaieServlet extends HttpServlet {
    private final FicheDePaieDAO ficheDAO = new FicheDePaieDAO();
    private final EmployeDAO employeDAO = new EmployeDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (user.getRole().getNomRole() != NomRole.ADMINISTRATEUR) {
            // Les non-admins n’ont accès qu’à la liste
            String action = request.getParameter("action");
            if (action != null && (action.equals("add"))) {
                response.sendRedirect("fiche?action=list");
                return;
            }
        }

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                request.setAttribute("employes", employeDAO.getAll());
                request.setAttribute("currentYear", java.time.Year.now().getValue());
                request.getRequestDispatcher("jsp/fiches-form.jsp").forward(request, response);
                break;

            default:
                List<FicheDePaie> fiches = new ArrayList<>();
                System.out.println("🔎 Rôle connecté : " + user.getRole().getNomRole());

                // Si ce n'est pas un admin, il ne voit que ses fiches de paie
                if (user.getRole().getNomRole() == NomRole.EMPLOYE) {
                    List <FicheDePaie> fiche_emp = ficheDAO.getByEmploye((int) user.getEmploye().getId());
                    if (fiche_emp != null) fiches.addAll(fiche_emp);
                }

                else if (user.getRole().getNomRole() == NomRole.CHEF_DE_PROJET) {
                    List <FicheDePaie> fiche_emp = ficheDAO.getByEmploye((int) user.getEmploye().getId());
                    if (fiche_emp != null) fiches.addAll(fiche_emp);
                }

                else if (user.getRole().getNomRole() == NomRole.CHEF_DE_DEPARTEMENT) {
                    List <FicheDePaie> fiche_emp = ficheDAO.getByEmploye((int) user.getEmploye().getId());
                    if (fiche_emp != null) fiches.addAll(fiche_emp);
                }

                // Si c’est un admin → il voit tout
                else if (user.getRole().getNomRole() == NomRole.ADMINISTRATEUR) {
                    fiches = ficheDAO.getAll();
                }

                else {
                    System.out.println("Role non vérifié !!!!");
                }

                request.setAttribute("fiches", fiches);
                request.getRequestDispatcher("jsp/fiches.jsp").forward(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idEmp = Integer.parseInt(request.getParameter("employeId"));
        int mois = Integer.parseInt(request.getParameter("mois"));
        int annee = Integer.parseInt(request.getParameter("annee"));
        Double deduction = Double.parseDouble(request.getParameter("deduction"));
        Double prime = Double.parseDouble(request.getParameter("prime"));
        Double salaireBase = Double.parseDouble(request.getParameter("salaireBase"));

        Employe e = employeDAO.getById(idEmp);
        if (e == null) {
            response.sendRedirect("employe?action=list");
            return;
        }

        FicheDePaie f = new FicheDePaie();
        f.setEmploye(e);
        f.setMois(mois);
        f.setAnnee(annee);
        f.setDeduction(deduction);
        f.setPrime(prime);
        f.setSalaireBase(salaireBase);
        f.getNetAPayer();
        f.setDate_generation(LocalDate.now());

        ficheDAO.save(f);
        response.sendRedirect("fiche");
    }
}