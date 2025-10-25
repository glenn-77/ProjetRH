<%@ page contentType="text/html;charset=UTF-8" import="java.util.*,com.example.model.FicheDePaie" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Fiches de Paie</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<jsp:include page="navbar.jsp" />

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">Fiches de Paie</h2>
        <a href="${pageContext.request.contextPath}/fiche?action=add" class="btn btn-success">+ Ajouter une fiche</a>
    </div>

    <table class="table table-striped table-bordered shadow-sm">
        <thead class="table-light">
        <tr>
            <th>ID</th>
            <th>Employé</th>
            <th>Mois</th>
            <th>Année</th>
            <th>Primes (€)</th>
            <th>Déductions (€)</th>
            <th>Net à Payer (€)</th>
            <th>Généré le</th>
        </tr>
        </thead>
        <tbody>
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
            <td><%=f.getDateGeneration()%></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

</body>
</html>
