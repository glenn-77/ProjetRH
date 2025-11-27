package com.example.controller;

import com.example.dao.EmployeDAO;
import com.example.dao.UtilisateurDAO;
import com.example.model.Employe;
import com.example.model.Utilisateur;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {
    private final EmployeDAO employeDAO = new EmployeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Récupération de l’utilisateur connecté depuis la session
        Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        //Récupération complète de l’employé associé
        Employe employe = employeDAO.getById((int) user.getEmploye().getId());
        request.setAttribute("employe", employe);

        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/profil.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Récupération de l’utilisateur connecté depuis la session
        Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String nouveauMotDePasse = request.getParameter("motDePasse");
        if (nouveauMotDePasse != null && !nouveauMotDePasse.trim().isEmpty()) {
            user.setMotDePasse(nouveauMotDePasse);
            new UtilisateurDAO().update(user);
            request.setAttribute("message", "Mot de passe mis à jour avec succès !");
        }

        doGet(request, response);
    }
}
