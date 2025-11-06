<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
    <div class="container">
        <!-- Bouton Accueil -->
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/index.jsp">Système RH</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto align-items-center">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/index.jsp">Accueil</a>
                </li>
                <!-- Employés visible UNIQUEMENT pour l'ADMIN -->
                <c:if test="${role == 'ADMINISTRATEUR'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/employe?action=list">Employés</a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/departement?action=list">Départements</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/projet?action=list">Projets</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/fiche">Fiches de paie</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/profil">Mon Profil</a>
                </li>
                <li class="nav-item ms-2">
                    <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/auth?action=logout">Déconnexion</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<style>
    .nav-link {
        color: white !important;
        margin-left: 0.8rem;
        border-radius: 6px;
        padding: 6px 10px;
        transition: all 0.2s ease-in-out;
    }

    .nav-link:hover {
        background-color: white !important;
        color: #0d6efd !important;
    }

    .btn-danger {
        background-color: #dc3545;
        border: none;
        transition: all 0.2s ease-in-out;
    }

    .btn-danger:hover {
        background-color: white !important;
        color: #dc3545 !important;
        border: 1px solid #dc3545;
    }
</style>
