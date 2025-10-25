<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Formulaire Projet</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<jsp:include page="navbar.jsp" />

<div class="container mt-5">
    <div class="card shadow-sm p-4 mx-auto" style="max-width:600px;">
        <h2 class="text-center mb-4">${projet != null ? "Modifier un Projet" : "Créer un Projet"}</h2>

        <form action="${pageContext.request.contextPath}/projet" method="post">
            <c:if test="${projet != null}">
                <input type="hidden" name="id" value="${projet.id}">
            </c:if>

            <div class="mb-3">
                <label class="form-label">Nom du projet</label>
                <input type="text" name="nom" class="form-control" value="${projet.nom}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Description</label>
                <textarea name="description" class="form-control" rows="4" required>${projet.description}</textarea>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Date début</label>
                    <input type="date" name="dateDebut" class="form-control" value="${projet.dateDebut}" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Date fin</label>
                    <input type="date" name="dateFin" class="form-control" value="${projet.dateFin}" required>
                </div>
            </div>

            <div class="text-center">
                <button type="submit" class="btn btn-primary px-4">${projet != null ? "Mettre à jour" : "Créer"}</button>
                <a href="${pageContext.request.contextPath}/projet?action=list" class="btn btn-secondary ms-2">Annuler</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
