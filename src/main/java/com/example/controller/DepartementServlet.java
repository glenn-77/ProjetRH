package com.example.controller;

import com.example.dao.DepartementDAO;
import com.example.dao.EmployeDAO;
import com.example.model.Departement;

import com.example.model.Employe;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/departement")
public class DepartementServlet extends HttpServlet {
    private final DepartementDAO departementDAO = new DepartementDAO();
    private final EmployeDAO employeDAO = new EmployeDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) action = "list";

        switch (action) {
            case "add":
                request.setAttribute("employes", new EmployeDAO().getAll());
                request.getRequestDispatcher("jsp/departements-form.jsp").forward(request, response);
                break;
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Departement departement = departementDAO.getById(idEdit);
                List<Employe> employes = employeDAO.getAll();
                request.setAttribute("departement", departement);
                request.setAttribute("employes", employes);
                request.getRequestDispatcher("jsp/departements-form.jsp").forward(request, response);
                break;
            case "delete":
                int idDel = Integer.parseInt(request.getParameter("id"));
                departementDAO.delete(idDel);
                response.sendRedirect("departement?action=list");
                break;
            default:
                List<Departement> list = departementDAO.getAll();
                request.setAttribute("departements", list);
                request.getRequestDispatcher("jsp/departements.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        String nom = request.getParameter("nom");
        String chefIdStr = request.getParameter("chefId");
        String[] employeIds = request.getParameterValues("employes");

        Employe chef = null;
        if (chefIdStr != null && !chefIdStr.isEmpty()) {
            chef = new Employe();
            chef.setId(Integer.parseInt(chefIdStr));
        }

        Departement d = new Departement();
        d.setNom(nom);
        d.setChef(chef);

        if (idStr != null && !idStr.isEmpty()) {
            d.setId(Long.parseLong(idStr));
            // 🔹 On transmet les IDs d’employés sélectionnés
            departementDAO.update(d, employeIds);
        } else {
            // 🔹 Pour la création, on lie directement les employés
            if (employeIds != null) {
                Set<Employe> selected = new HashSet<>();
                for (String empId : employeIds) {
                    Employe e = new Employe();
                    e.setId(Long.parseLong(empId));
                    selected.add(e);
                }
                d.setEmployes(selected);
            }
            departementDAO.save(d);
        }
        response.sendRedirect("departement?action=list");
    }
}
