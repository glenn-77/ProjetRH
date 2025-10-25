<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Projets</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<jsp:include page="navbar.jsp" />

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">Projets</h2>
        <a href="${pageContext.request.contextPath}/projet?action=add" class="btn btn-success">+ Ajouter un projet</a>
    </div>

    <table class="table table-striped table-bordered shadow-sm">
        <thead class="table-light">
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Description</th>
            <th>Date début</th>
            <th>Date fin</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="projet" items="${projets}">
            <tr>
                <td>${projet.id}</td>
                <td>${projet.nom}</td>
                <td>${projet.description}</td>
                <td>${projet.dateDebut}</td>
                <td>${projet.dateFin}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/projet?action=edit&id=${projet.id}" class="btn btn-sm btn-primary">Modifier</a>
                    <a href="${pageContext.request.contextPath}/projet?action=delete&id=${projet.id}"
                       class="btn btn-sm btn-outline-danger"
                       onclick="return confirm('Voulez-vous vraiment supprimer ce projet ?');">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
