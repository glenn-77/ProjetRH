<%@ page contentType="text/html;charset=UTF-8" import="com.example.model.Employe, com.example.model.Projet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Profil - ${employe.prenom} ${employe.nom}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<jsp:include page="navbar.jsp" />

<div class="container mt-5">
    <h2 class="fw-bold mb-4">Mon Profil</h2>

    <div class="card shadow-sm mb-4">
        <div class="card-body">
            <h5 class="card-title">${employe.prenom} ${employe.nom}</h5>
            <p class="card-text"><strong>Matricule :</strong> ${employe.matricule}</p>
            <p class="card-text"><strong>Département :</strong> ${employe.departement.nom}</p>
            <p class="card-text"><strong>Poste :</strong> ${employe.poste}</p>
            <p class="card-text"><strong>Grade :</strong> ${employe.grade}</p>

            <h6>Projets :</h6>
            <ul>
                <c:forEach var="p" items="${employe.projets}">
                    <li>${p.nom}</li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div class="card shadow-sm mb-4">
        <div class="card-body">
            <h5 class="card-title">Modifier mon mot de passe</h5>
            <form method="post" action="${pageContext.request.contextPath}/profil" class="d-flex gap-2">
                <input type="password" name="motDePasse" class="form-control" placeholder="Nouveau mot de passe" required />
                <button type="submit" class="btn btn-primary">Mettre à jour</button>
            </form>
            <p class="mt-2 text-success">${message}</p>
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/employe?action=list" class="btn btn-secondary">← Retour</a>
</div>

</body>
</html>
