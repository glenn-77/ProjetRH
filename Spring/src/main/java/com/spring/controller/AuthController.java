package com.spring.controller;

import com.spring.model.*;
import com.spring.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UtilisateurService utilisateurService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // login.jsp
    }

    @PostMapping("/login")
    public String login(@RequestParam String login,
                        @RequestParam String motDePasse,
                        HttpSession session,
                        Model model) {

        Utilisateur user = utilisateurService.login(login, motDePasse);

        if (user == null) {
            model.addAttribute("error", "Identifiants invalides");
            return "login";
        }

        session.setAttribute("user", user);
        return "redirect:/employes"; // ou /home
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
