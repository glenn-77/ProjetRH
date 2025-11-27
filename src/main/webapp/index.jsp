<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Accueil - Gestion RH</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f8f9fa;
        }
        footer {
            background-color: #0d6efd;
            color: #fff;
            text-align: center;
            padding: 0.8rem;
            position: fixed;
            bottom: 0;
            width: 100%;
            font-size: 0.9rem;
        }
    </style>
</head>

<body>

<jsp:include page="jsp/navbar.jsp"/>


<section class="hero" style="text-align: center;
    height: 60vh;
    padding: 40px 20px;
    color: black;
    font-weight: bold;
    text-decoration: underline;
    background-image: url('https://www.riseup.ai/hubfs/Hubspot%20-%20Featured%20photo%20%20(46).jpg');
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    opacity: 0.7;">
    <h2>Bienvenue ${user.employe.prenom} ${user.employe.nom}!</h2>
</section>

<section class="cards">

    <c:if test="${role == 'ADMINISTRATEUR'}">
        <div class="card">
            <div class="icon">&#128101;</div>
            <h3>Employés</h3>
            <p>Gérer vos employés</p>
            <a href="${pageContext.request.contextPath}/employe?action=list" class="btn">En savoir plus</a>
        </div>
    </c:if>

    <div class="card">
        <div class="icon">&#127980;</div>
        <h3>Département</h3>
        <c:if test="${role == 'ADMINISTRATEUR' || role == 'CHEF_DE_DEPARTEMENT'}">
            <p>Gérer votre département</p>
        </c:if>
        <c:if test="${not(role == 'ADMINISTRATEUR' || role == 'CHEF_DE_DEPARTEMENT')}">
            <p>Voir votre département</p>
        </c:if>
        <a href="${pageContext.request.contextPath}/departement?action=list" class="btn">En savoir plus</a>
    </div>

    <div class="card">
        <div class="icon">&#128188;</div>
        <h3>Projets</h3>
        <c:if test="${role == 'ADMINISTRATEUR' || role == 'CHEF_DE_DEPARTEMENT' || role == 'CHEF_DE_PROJET'}">
            <p>Gérer vos différents projets</p>
        </c:if>
        <c:if test="${role == 'EMPLOYE'}">
            <p>Voir vos différents projets</p>
        </c:if>
        <a href="${pageContext.request.contextPath}/projet?action=list" class="btn">En savoir plus</a>
    </div>

    <div class="card">
        <div class="icon">&#128196;</div>
        <h3>Fiche de paie</h3>
        <c:if test="${role == 'ADMINISTRATEUR'}">
            <p>Gérer les fiches de paie de vos employés</p>
        </c:if>
        <c:if test="${not(role == 'ADMINISTRATEUR')}">
            <p>Voir vos fiches de paie</p>
        </c:if>
        <a href="${pageContext.request.contextPath}/fiche?action=list" class="btn">En savoir plus</a>
    </div>

    <div class="card">
        <div class="icon">&#128100;</div>
        <h3>Profil</h3>
        <p>Voir votre profil</p>
        <a href="${pageContext.request.contextPath}/profil?action=list" class="btn">En savoir plus</a>
    </div>

</section>


<!-- FOOTER -->
<footer>
    &copy; 2025 - Système de Gestion RH
</footer>

</body>
</html>
