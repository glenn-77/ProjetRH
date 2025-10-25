<%@ page contentType="text/html;charset=UTF-8" import="java.util.*,com.example.model.Departement" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Départements</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<jsp:include page="navbar.jsp" />


<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">Liste des Départements</h2>
        <a href="departement?action=add" class="btn btn-primary">+ Ajouter un Département</a>
    </div>

    <table class="table table-striped table-bordered shadow-sm align-middle">
        <thead class="table-light">
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Chef de Département</th>
            <th class="text-center">Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Departement> depts = (List<Departement>) request.getAttribute("departements");
            for (Departement d : depts) {
        %>
        <tr>
            <td><%=d.getId()%></td>
            <td><%=d.getNom()%></td>
            <td><%=d.getChef() != null ? d.getChef().getNom() + " " + d.getChef().getPrenom() : "—"%></td>
            <td class="text-center">
                <a href="departement?action=delete&id=<%=d.getId()%>"
                   class="btn btn-sm btn-outline-danger">Supprimer</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
