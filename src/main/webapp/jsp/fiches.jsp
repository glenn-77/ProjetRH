<%@ page contentType="text/html;charset=UTF-8" import="java.util.*,com.example.model.FicheDePaie" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="role" value="${sessionScope.role}" />
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
    <c:if test="${role == 'ADMINISTRATEUR'}">
        <a href="${pageContext.request.contextPath}/fiche?action=add" class="btn btn-success">+ Ajouter une fiche</a>
    </c:if>
    </div>

    <form action="fiche" method="get" class="d-flex gap-3 align-items-end">
        <input type="hidden" name="action" value="search">

        <div>
            <label for="employeId" class="form-label">Employé</label>
            <select name="employeId" id="employeId" class="form-select">
                <option value="">-- Tous --</option>
                <c:forEach var="e" items="${employes}">
                    <option value="${e.id}" ${e.id == param.employeId ? 'selected' : ''}>${e.prenom} ${e.nom}</option>
                </c:forEach>
            </select>
        </div>

        <div>
            <label for="dateDebut" class="form-label">Date début</label>
            <input type="date" name="dateDebut" id="dateDebut" value="${param.dateDebut}" class="form-control">
        </div>

        <div>
            <label for="dateFin" class="form-label">Date fin</label>
            <input type="date" name="dateFin" id="dateFin" value="${param.dateFin}" class="form-control">
        </div>

        <button type="submit" class="btn btn-primary">Rechercher</button>
        <a href="${pageContext.request.contextPath}/fiche?action=list" class="btn btn-secondary">Réinitialiser</a>
    </form>


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
            <th>Actions</th>
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
            <td>
                <!-- 🔹 Bouton Export PDF -->
                <a href="${pageContext.request.contextPath}/fiche?action=export&id=<%=f.getId()%>"
                   class="btn btn-danger btn-sm">
                    <i class="bi bi-file-earmark-pdf"></i> Exporter PDF
                </a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

</body>
</html>
