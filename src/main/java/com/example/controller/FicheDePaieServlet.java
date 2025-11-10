package com.example.controller;

import com.example.dao.FicheDePaieDAO;
import com.example.dao.EmployeDAO;
import com.example.model.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/fiche")
public class FicheDePaieServlet extends HttpServlet {
    private final FicheDePaieDAO ficheDAO = new FicheDePaieDAO();
    private final EmployeDAO employeDAO = new EmployeDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (user.getRole().getNomRole() != NomRole.ADMINISTRATEUR) {
            // Les non-admins n’ont accès qu’à la liste
            String action = request.getParameter("action");
            if (action != null && (action.equals("add"))) {
                response.sendRedirect("fiche?action=list");
                return;
            }
        }

        String action = request.getParameter("action");
        if (action == null) action = "list";
        List<FicheDePaie> fiches = new ArrayList<>();

        switch (action) {

            case "add":
                request.setAttribute("employes", employeDAO.getAll());
                request.setAttribute("currentYear", java.time.Year.now().getValue());
                request.getRequestDispatcher("jsp/fiches-form.jsp").forward(request, response);
                break;

            case "search":
                String employeStr = request.getParameter("employeId");
                String debutStr = request.getParameter("dateDebut");
                String finStr = request.getParameter("dateFin");

                Integer employeId = (employeStr != null && !employeStr.isEmpty())
                        ? Integer.parseInt(employeStr)
                        : null;
                LocalDate dateDebut = (debutStr != null && !debutStr.isEmpty())
                        ? LocalDate.parse(debutStr)
                        : null;
                LocalDate dateFin = (finStr != null && !finStr.isEmpty())
                        ? LocalDate.parse(finStr)
                        : null;

                fiches = ficheDAO.search(employeId, dateDebut, dateFin);

                request.setAttribute("fiches", fiches);
                request.setAttribute("employes", employeDAO.getAll());
                request.getRequestDispatcher("jsp/fiches.jsp").forward(request, response);
                break;

            case "export":
                String idStr = request.getParameter("id");
                if (idStr == null || idStr.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/fiche");
                    return;
                }

                int id = Integer.parseInt(idStr);
                FicheDePaie fiche = ficheDAO.getById(id);

                try {
                    // 1️⃣ Génère le PDF sur le serveur
                    String path = getServletContext().getRealPath("/pdf/fiches/fiche_"
                            + fiche.getEmploye().getNom() + "_"
                            + fiche.getAnnee() + "-" + fiche.getMois() + ".pdf");

                    generateFichePaiePDF(fiche, path);

                    // 2️⃣ Configure la réponse pour téléchargement
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition",
                            "attachment; filename=\"fiche_" + fiche.getEmploye().getNom() + "_"
                                    + fiche.getAnnee() + "-" + fiche.getMois() + ".pdf\"");

                    // 3️⃣ Envoie le fichier dans la réponse HTTP
                    java.io.File pdfFile = new java.io.File(path);
                    try (java.io.FileInputStream in = new java.io.FileInputStream(pdfFile);
                         java.io.OutputStream out = response.getOutputStream()) {

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = in.read(buffer)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }
                        out.flush();
                    }

                } catch (Exception e) {
                    throw new ServletException("Erreur lors de la génération ou du téléchargement du PDF : " + e.getMessage(), e);
                }
                break;

            default:

                System.out.println("🔎 Rôle connecté : " + user.getRole().getNomRole());

                // Si ce n'est pas un admin, il ne voit que ses fiches de paie
                if (user.getRole().getNomRole() == NomRole.EMPLOYE) {
                    List <FicheDePaie> fiche_emp = ficheDAO.getByEmploye((int) user.getEmploye().getId());
                    if (fiche_emp != null) fiches.addAll(fiche_emp);
                }

                else if (user.getRole().getNomRole() == NomRole.CHEF_DE_PROJET) {
                    List <FicheDePaie> fiche_emp = ficheDAO.getByEmploye((int) user.getEmploye().getId());
                    if (fiche_emp != null) fiches.addAll(fiche_emp);
                }

                else if (user.getRole().getNomRole() == NomRole.CHEF_DE_DEPARTEMENT) {
                    List <FicheDePaie> fiche_emp = ficheDAO.getByEmploye((int) user.getEmploye().getId());
                    if (fiche_emp != null) fiches.addAll(fiche_emp);
                }

                // Si c’est un admin → il voit tout
                else if (user.getRole().getNomRole() == NomRole.ADMINISTRATEUR) {
                    fiches = ficheDAO.getAll();
                }

                else {
                    System.out.println("Role non vérifié !!!!");
                }

                request.setAttribute("fiches", fiches);
                request.setAttribute("employes", employeDAO.getAll());
                request.getRequestDispatcher("jsp/fiches.jsp").forward(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idEmp = Integer.parseInt(request.getParameter("employeId"));
        int mois = Integer.parseInt(request.getParameter("mois"));
        int annee = Integer.parseInt(request.getParameter("annee"));
        Double deduction = Double.parseDouble(request.getParameter("deduction"));
        Double prime = Double.parseDouble(request.getParameter("prime"));
        Double salaireBase = Double.parseDouble(request.getParameter("salaireBase"));

        Employe e = employeDAO.getById(idEmp);
        if (e == null) {
            response.sendRedirect("employe?action=list");
            return;
        }

        FicheDePaie f = new FicheDePaie();
        f.setEmploye(e);
        f.setMois(mois);
        f.setAnnee(annee);
        f.setDeduction(deduction);
        f.setPrime(prime);
        f.setSalaireBase(salaireBase);
        f.getNetAPayer();
        f.setDate_generation(LocalDate.now());

        ficheDAO.save(f);
        response.sendRedirect("fiche");
    }

    public static void generateFichePaiePDF(FicheDePaie fiche, String filePath) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // 🔹 En-tête
        Paragraph header = new Paragraph("FICHE DE PAIE", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(new Paragraph("\n"));

        // 🔹 Infos Employé
        document.add(new Paragraph("Nom : " + fiche.getEmploye().getNom()));
        document.add(new Paragraph("Prénom : " + fiche.getEmploye().getPrenom()));
        document.add(new Paragraph("Poste : " + fiche.getEmploye().getPoste()));
        document.add(new Paragraph("Date d’embauche : " + fiche.getEmploye().getDateEmbauche()));
        document.add(new Paragraph("Date de génération : " + fiche.getDateGeneration()));
        document.add(new Paragraph("\n"));

        // 🔹 Tableau des montants
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        table.addCell("Salaire de base");
        table.addCell(fiche.getSalaireBase() + " €");

        table.addCell("Prime");
        table.addCell(fiche.getPrime() + " €");

        table.addCell("Déductions");
        table.addCell(fiche.getDeduction() + " €");

        table.addCell("Salaire Net à Payer");
        table.addCell(fiche.getNetAPayer() + " €");

        document.add(table);
        document.add(new Paragraph("\n"));

        // 🔹 Pied de page
        Paragraph footer = new Paragraph("Document généré automatiquement — " + LocalDate.now(),
                new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC));
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
    }
}