package com.example.controller;

import com.example.dao.DepartementDAO;
import com.example.dao.EmployeDAO;
import com.example.model.Departement;

import com.example.model.Employe;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/departement")
public class DepartementServlet extends HttpServlet {
    private final DepartementDAO departementDAO = new DepartementDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) action = "list";

        switch (action) {
            case "add":
                request.setAttribute("employes", new EmployeDAO().getAll());
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Departement d = new Departement();
        d.setNom(request.getParameter("nom"));
        String chefIdStr = request.getParameter("chefId");
        if (chefIdStr != null && !chefIdStr.isEmpty()) {
            int chefId = Integer.parseInt(chefIdStr);
            Employe chef = new Employe();
            chef.setId(chefId);
            d.setChef(chef);
        }
        departementDAO.save(d);
        response.sendRedirect("departement?action=list");
    }
}
