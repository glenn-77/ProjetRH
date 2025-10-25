<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Accueil - Gestion RH</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-bold" href="#">Système RH</a>
        <div>
            <a class="nav-link text-white" href="employe?action=list">Employés</a>
            <a class="nav-link text-white" href="departement?action=list">Départements</a>
            <a class="nav-link text-white" href="projet?action=list">Projets</a>
            <a class="nav-link text-white" href="fiche">Fiches de paie</a>
        </div>
    </div>
</nav>

<div class="container text-center mt-5">
    <h1 class="mb-4">Bienvenue dans le système de gestion RH</h1>
    <p class="lead">Gérez facilement vos employés, départements, projets et fiches de paie.</p>
    <a href="employe?action=list" class="btn btn-primary btn-lg mt-3">Commencer</a>
</div>

</body>
</html>