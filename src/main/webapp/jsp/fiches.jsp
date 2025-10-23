<%@ page contentType="text/html;charset=UTF-8" import="java.util.*,com.example.model.FicheDePaie" %>
<html>
<head><title>Fiches de Paie</title></head>
<body>
<h2>Liste des Fiches de Paie</h2>
<a href="${pageContext.request.contextPath}/fiche?action=add" class="btn btn-primary">Ajouter une Fiche de Paie</a>
<br/><br/>
<table border="1">
    <tr><th>ID</th><th>Employé</th><th>Mois</th><th>Année</th><th>Primes (€)</th><th>Déductions (€)</th><th>Net à Payer (€)</th><th>Généré le</th></tr>
    <%
        List<FicheDePaie> fiches = (List<FicheDePaie>) request.getAttribute("fiches");
        for (FicheDePaie f : fiches) {
    %>
    <tr>
        <td><%=f.getId()%></td>
        <td><%=f.getEmploye().getNom()%> <%=f.getEmploye().getPrenom()%></td>
        <td><%=f.getMois()%></td>
        <td><%=f.getAnnee()%></td>
        <td><%=f.getPrime()%></td>
        <td><%=f.getDeduction()%></td>
        <td><%=f.getNetAPayer()%></td>
        <td><%=f.getDate_generation()%></td>
    </tr>
    <% } %>
</table>
</body>
</html>