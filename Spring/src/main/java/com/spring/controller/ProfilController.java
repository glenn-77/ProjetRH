package com.spring.controller;

import com.spring.model.*;
import com.spring.service.EmployeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProfilController {

    private final EmployeService employeService;

    @GetMapping("/profil")
    public String profil(HttpSession session, Model model) {
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Employe employe = user.getEmploye();
        model.addAttribute("user", user);
        model.addAttribute("employe", employe);

        return "profil"; // profil.jsp
    }
}
