package com.example.controller;

import com.example.dao.ProjetDAO;
import com.example.model.EtatProjet;
import com.example.model.Projet;

import jakarta.ejb.Local;
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
    private ProjetDAO projetDAO = new ProjetDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        EmployeDAO employeDAO = new EmployeDAO();
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Projet p = new Projet();
        String idStr = request.getParameter("id");

        if (idStr != null && !idStr.isEmpty()) {
            p.setId(Integer.parseInt(idStr));
        }
        p.setNom(request.getParameter("nom"));
        p.setDescription(request.getParameter("description"));
        p.setEtat(EtatProjet.valueOf(request.getParameter("etat")));
        p.setBudget(Double.parseDouble(request.getParameter("budget")));
        p.setDateDebut(Date.valueOf(request.getParameter("dateDebut")));
        p.setDateFin(Date.valueOf(request.getParameter("dateFin")));
        String chefIdStr = request.getParameter("chefId");

        if (chefIdStr != null && !chefIdStr.isEmpty()) {
            Employe chef = new Employe();
            chef.setId(Integer.parseInt(chefIdStr));
            p.setChefProjet(chef);
        }

        String[] employesIds = request.getParameterValues("employesAssignes");
        if (employesIds != null) {
            Set<Employe> employes = new HashSet<>();
            for (String id : employesIds) {
                Employe e = new Employe();
                e.setId(Integer.parseInt(id));
                employes.add(e);
            }
            p.setEmployes(employes);
        }

        // 🔄 Si un ID existe => update, sinon => save
        if (idStr != null && !idStr.isEmpty()) {
            projetDAO.update(p);
        } else {
            projetDAO.save(p);
        }

        projetDAO.save(p);
        response.sendRedirect("projet?action=list");
    }
}