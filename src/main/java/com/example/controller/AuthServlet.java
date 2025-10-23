package com.example.controller;

import com.example.dao.UtilisateurDAO;
import com.example.model.Utilisateur;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String mdp = request.getParameter("motDePasse");

        Utilisateur u = utilisateurDAO.getByLogin(login, mdp);

        if (u != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", u);
            response.sendRedirect("index.jsp");
        } else {
            request.setAttribute("error", "Identifiants invalides");
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        response.sendRedirect("jsp/login.jsp");
    }
}
