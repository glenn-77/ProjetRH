package com.spring.controller;

import com.spring.model.*;
import com.spring.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/departements")
public class DepartementController {

    private final DepartementService departementService;
    private final EmployeService employeService;

    @GetMapping
    public String listDepartements(Model model) {
        model.addAttribute("departements", departementService.getAll());
        return "departements"; // departements.jsp
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("departement", new Departement());
        model.addAttribute("employes", employeService.getAll());
        return "departements-form"; // departements-form.jsp
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Departement d = departementService.getById(id);
        if (d == null) return "redirect:/departements";

        model.addAttribute("departement", d);
        model.addAttribute("employes", employeService.getAll());
        return "departements-form";
    }

    @PostMapping("/save")
    public String saveDepartement(@ModelAttribute Departement departement,
                                  @RequestParam(value = "chefId", required = false) Long chefId,
                                  @RequestParam(value = "employesIds", required = false) List<Long> employesIds) {

        boolean isNew = (departement.getId() == null);
        Departement saved = isNew
                ? departementService.create(departement)
                : departementService.update(departement.getId(), departement);

        // Chef
        if (chefId != null) {
            departementService.setChef(saved.getId(), chefId);
        }

        // Employés du département
        if (employesIds != null) {
            departementService.assignEmployes(saved.getId(), employesIds);
        }

        return "redirect:/departements";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartement(@PathVariable Long id) {
        departementService.delete(id);
        return "redirect:/departements";
    }
}