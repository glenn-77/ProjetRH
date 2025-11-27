<%@ page contentType="text/html;charset=UTF-8" import="java.util.*,com.example.model.Employe" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des employés</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<jsp:include page="navbar.jsp" />

<div class="container mt-5">
    <!-- Titre centré, comme départements -->
    <h2 class="text-center mb-4">Gestion des employés</h2>

    <!-- Barre d’actions -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/employe?action=list" class="btn btn-secondary btn-sm">Tous</a>
            <a href="${pageContext.request.contextPath}/employe?action=listByGrade" class="btn btn-primary btn-sm">Par grade</a>
            <a href="${pageContext.request.contextPath}/employe?action=listByPoste" class="btn btn-success btn-sm">Par poste</a>
        </div>
        <a href="employe?action=add" class="btn btn-success">+ Ajouter un employé</a>
    </div>

    <!-- Recherche -->
    <form class="d-flex gap-2 align-items-end mb-3" action="${pageContext.request.contextPath}/employe" method="get">
        <input type="hidden" name="action" value="search">
        <div class="flex-grow-1">
            <input class="form-control" type="search" name="keyword" placeholder="Rechercher..."
                   value="${keyword != null ? keyword : ''}" required>
        </div>
        <button class="btn btn-primary" type="submit">Rechercher</button>
        <a href="${pageContext.request.contextPath}/employe?action=list" class="btn btn-outline-secondary">Réinitialiser</a>
    </form>

    <!-- Tableau : mêmes classes que départements -->
    <table class="table table-bordered table-hover align-middle shadow-sm">
        <thead class="table-dark text-center">
        <tr>
            <th>Matricule</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Département</th>
            <th>Grade</th>
            <th>Poste</th>
            <th>Rôle</th>
            <th class="text-center">Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Employe> employes = (List<Employe>) request.getAttribute("employes");
            for (Employe e : employes) {
        %>
        <tr>
            <td><%= e.getMatricule() %></td>
            <td><%= e.getNom() %></td>
            <td><%= e.getPrenom() %></td>
            <td><%= (e.getDepartement()!=null ? e.getDepartement().getNom() : "—") %></td>
            <td><%= e.getGrade() %></td>
            <td><%= e.getPoste() %></td>
            <td><%=e.getRoleNom()%></td>
            <td class="text-center">
                <a href="employe?action=affecter&id=<%= e.getId() %>" class="btn btn-sm btn-outline-success">Affecter</a>
                <a href="employe?action=edit&id=<%= e.getId() %>" class="btn btn-sm btn-warning ms-1">Modifier</a>
                <a href="employe?action=delete&id=<%= e.getId() %>"
                   onclick="return confirm('Supprimer cet employé ?')"
                   class="btn btn-sm btn-danger ms-1">Supprimer</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<!-- FOOTER -->
<footer>
    &copy; 2025 - Système de Gestion RH
</footer>
</body>
</html>