package com.example.controller;

import com.example.dao.FicheDePaieDAO;
import com.example.dao.EmployeDAO;
import com.example.model.FicheDePaie;
import com.example.model.Employe;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/fiche")
public class FicheDePaieServlet extends HttpServlet {
    private final FicheDePaieDAO ficheDAO = new FicheDePaieDAO();
    private final EmployeDAO employeDAO = new EmployeDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                request.setAttribute("employes", employeDAO.getAll());
                request.setAttribute("currentYear", java.time.Year.now().getValue());
                request.getRequestDispatcher("jsp/fiches-form.jsp").forward(request, response);
                break;

            default:
                List<FicheDePaie> fiches = ficheDAO.getAll();
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