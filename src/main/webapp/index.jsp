<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Accueil - Gestion RH</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f8f9fa;
        }
        nav.navbar {
            padding: 0.8rem 1rem;
        }
        .nav-link {
            color: white !important;
            margin-left: 1rem;
            transition: color 0.2s, background-color 0.2s;
            border-radius: 6px;
            padding: 6px 10px;
        }
        .nav-link:hover {
            background-color: white !important;
            color: #0d6efd !important;
        }
        .btn-logout {
            background-color: #dc3545;
            border: none;
            padding: 0.45rem 0.9rem;
            border-radius: 0.5rem;
            color: white;
            font-weight: 500;
            margin-left: 1rem;
            transition: all 0.2s ease-in-out;
        }
        .btn-logout:hover {
            background-color: white;
            color: #dc3545;
            border: 1px solid #dc3545;
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
        .welcome {
            text-align: center;
            margin-top: 7rem;
        }
    </style>
</head>

<body>

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-bold" href="#">Système RH</a>
        <div class="ms-auto d-flex align-items-center">
            <a class="nav-link" href="employe?action=list">Employés</a>
            <a class="nav-link" href="departement?action=list">Départements</a>
            <a class="nav-link" href="projet?action=list">Projets</a>
            <a class="nav-link" href="fiche">Fiches de paie</a>
            <a href="${pageContext.request.contextPath}/auth?action=logout" class="btn-logout">Déconnexion</a>
        </div>
    </div>
</nav>

<!-- CONTENU -->
<main class="container">
    <div class="welcome">
        <h1 class="fw-bold mb-3">Bienvenue dans le système de gestion RH</h1>
        <p class="lead">Gérez facilement vos employés, départements, projets et fiches de paie.</p>
    </div>
</main>

<!-- FOOTER -->
<footer>
    &copy; 2025 - Système de Gestion RH
</footer>

</body>
</html>
