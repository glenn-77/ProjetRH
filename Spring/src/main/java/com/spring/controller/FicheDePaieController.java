package com.spring.controller;

import com.spring.model.*;
import com.spring.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fiches")
public class FicheDePaieController {

    private final FicheDePaieService ficheDePaieService;
    private final EmployeService employeService;

    @GetMapping
    public String listFiches(@RequestParam(value = "employeId", required = false) Long employeId,
                             @RequestParam(value = "dateDebut", required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
                             @RequestParam(value = "dateFin", required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
                             Model model) {

        List<FicheDePaie> fiches;

        if (employeId != null && dateDebut != null && dateFin != null) {
            fiches = ficheDePaieService.search(employeId, dateDebut, dateFin);
        } else {
            fiches = ficheDePaieService.getAll();
        }

        model.addAttribute("fiches", fiches);
        model.addAttribute("employes", employeService.getAll());
        model.addAttribute("employeId", employeId);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);

        return "fiches"; // fiches.jsp
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("fiche", new FicheDePaie());
        model.addAttribute("employes", employeService.getAll());
        return "fiches-form"; // fiches-form.jsp
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        FicheDePaie f = ficheDePaieService.getById(id);
        if (f == null) return "redirect:/fiches";

        model.addAttribute("fiche", f);
        model.addAttribute("employes", employeService.getAll());
        return "fiches-form";
    }

    @PostMapping("/save")
    public String saveFiche(@RequestParam(value = "id", required = false) Long id,
                            @RequestParam Long employeId,
                            @RequestParam int mois,
                            @RequestParam int annee,
                            @RequestParam Double salaireBase,
                            @RequestParam(required = false, defaultValue = "0") Double prime,
                            @RequestParam(required = false, defaultValue = "0") Double deduction) {

        FicheDePaie data = new FicheDePaie();
        data.setMois(mois);
        data.setAnnee(annee);
        data.setSalaireBase(salaireBase);
        data.setPrime(prime);
        data.setDeduction(deduction);

        if (id == null) {
            ficheDePaieService.create(data, employeId);
        } else {
            data.setId(id);
            ficheDePaieService.update(id, data);
        }

        return "redirect:/fiches";
    }

    @GetMapping("/delete/{id}")
    public String deleteFiche(@PathVariable Long id) {
        ficheDePaieService.delete(id);
        return "redirect:/fiches";
    }
}
