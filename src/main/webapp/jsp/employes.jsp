<%@ page contentType="text/html;charset=UTF-8" import="java.util.*,com.example.model.Employe" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Employés</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<jsp:include page="navbar.jsp" />


<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">Liste des Employés</h2>
        <a href="employe?action=add" class="btn btn-primary">+ Ajouter un Employé</a>
        <a href="${pageContext.request.contextPath}/employe?action=list" class="btn btn-secondary btn-sm">Tous</a>
        <a href="${pageContext.request.contextPath}/employe?action=listByGrade" class="btn btn-primary btn-sm">Par grade</a>
        <a href="${pageContext.request.contextPath}/employe?action=listByPoste" class="btn btn-success btn-sm">Par poste</a>
    </div>
    <div class="d-flex justify-content-between align-items-center mb-3">
        <form class="d-flex" action="${pageContext.request.contextPath}/employe" method="get">
            <input type="hidden" name="action" value="search">
            <input class="form-control me-2" type="search" name="keyword" placeholder="Rechercher..."
               value="${keyword != null ? keyword : ''}" required>
            <button class="btn btn-primary" type="submit">Rechercher</button>

            <a href="${pageContext.request.contextPath}/employe?action=list" class="btn btn-secondary">Réinitialiser</a>

        </form>
    </div>

    <table class="table table-hover table-striped table-bordered shadow-sm">
        <thead class="table-light">
        <tr>
            <th>Matricule</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Département</th>
            <th>Grade</th>
            <th>Poste</th>
            <th class="text-center">Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Employe> employes = (List<Employe>) request.getAttribute("employes");
            for (Employe e : employes) {
        %>
        <tr>
            <td><%=e.getMatricule()%></td>
            <td><%=e.getNom()%></td>
            <td><%=e.getPrenom()%></td>
            <td><%= (e.getDepartement()!=null ? e.getDepartement().getNom() : "—") %></td>
            <td><%=e.getGrade()%></td>
            <td><%=e.getPoste()%></td>
            <td class="text-center">
                <a href="employe?action=affecter&id=<%=e.getId()%>"
                   class="btn btn-sm btn-outline-success">Affecter</a>
                <a href="employe?action=edit&id=<%=e.getId()%>" class="btn btn-sm btn-outline-primary">Modifier</a>
                <a href="employe?action=delete&id=<%=e.getId()%>" class="btn btn-sm btn-outline-danger ms-1">Supprimer</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>