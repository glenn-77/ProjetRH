package com.example.controller;

import com.example.dao.DepartementDAO;
import com.example.dao.EmployeDAO;
import com.example.model.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/departement")
public class DepartementServlet extends HttpServlet {
    private final DepartementDAO departementDAO = new DepartementDAO();
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
                response.sendRedirect("departement?action=list");
                return;
            }
        }

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
                List<Departement> list = new ArrayList<>();
                System.out.println("🔎 Rôle connecté : " + user.getRole().getNomRole());

                // Si ce n'est pas un admin, il ne voit que son département
                if (user.getRole().getNomRole() == NomRole.EMPLOYE) {
                    Departement dept = departementDAO.findByEmployeId((int) user.getEmploye().getId());
                    if (dept != null) list.add(dept);
                }

                else if (user.getRole().getNomRole() == NomRole.CHEF_DE_PROJET) {
                    Departement dept = departementDAO.findByEmployeId((int) user.getEmploye().getId());
                    if (dept != null) list.add(dept);
                }

                else if (user.getRole().getNomRole() == NomRole.CHEF_DE_DEPARTEMENT) {
                    Departement dept = departementDAO.findByChefId((int) user.getEmploye().getId());
                    if (dept != null) list.add(dept);
                }

                // Si c’est un admin → il voit tout
                else if (user.getRole().getNomRole() == NomRole.ADMINISTRATEUR) {
                    list = departementDAO.getAll();
                }

                else {
                    System.out.println("Role non vérifié !!!!");
                }

                request.setAttribute("departements", list);
                request.getRequestDispatcher("jsp/departements.jsp").forward(request, response);
                break;

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idStr = request.getParameter("id");
        String nom = request.getParameter("nom");
        String chefIdStr = request.getParameter("chefId");
        String[] employeIds = request.getParameterValues("employes");

        Departement d;

        // ✅ Si on modifie un département, on le récupère depuis la base
        if (idStr != null && !idStr.isEmpty()) {
            d = departementDAO.getById(Integer.parseInt(idStr));
        } else {
            d = new Departement();
        }

        d.setNom(nom);

        // 🔹 Gestion du chef
        Employe chef;
        if (chefIdStr != null && !chefIdStr.isEmpty()) {
            int chefId = Integer.parseInt(chefIdStr);
            chef = employeDAO.getById(chefId);

            // 🔍 Vérifie si le chef dirige déjà un autre département
            Departement existing = departementDAO.findByChefId(chefId);
            if (existing != null && (idStr == null || existing.getId() != d.getId())) {
                existing.setChef(null);
                departementDAO.update(existing, null);
                System.out.println("✅ " + chef.getNom() + " retiré du département : " + existing.getNom());
            }

            // ✅ Lien bidirectionnel
            chef.setDepartement(d);
            d.setChef(chef);
        } else {
            // Aucun chef sélectionné → on retire l'ancien si besoin
            d.setChef(null);
        }

        // 🔹 Mise à jour des employés du département
        Set<Employe> selected = new HashSet<>();
        if (employeIds != null) {
            for (String empId : employeIds) {
                Employe e = employeDAO.getById(Integer.parseInt(empId));
                e.setDepartement(d); // 🔁 Lien côté employé
                selected.add(e);
            }
        }
        d.setEmployes(selected);

        // 🔹 Enregistrement
        if (idStr != null && !idStr.isEmpty()) {
            departementDAO.update(d, employeIds);
        } else {
            departementDAO.save(d);
        }

        response.sendRedirect("departement?action=list");
    }

}
