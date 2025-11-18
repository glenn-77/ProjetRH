<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" import="java.util.*,com.example.model.Employe,com.example.model.FicheDePaie"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Formulaire Fiche de Paie</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<jsp:include page="navbar.jsp" />

<div class="container mt-5">
    <div class="card shadow-sm p-4 mx-auto" style="max-width:600px;">
        <h2 class="text-center mb-4">
            ${fiche != null ? "Modifier une Fiche de Paie" : "Créer une Fiche de Paie"}
        </h2>

        <form action="${pageContext.request.contextPath}/fiche" method="post">
            <c:if test="${fiche != null}">
                <input type="hidden" name="id" value="${fiche.id}">
            </c:if>

            <!-- Sélection de l'employé -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Employé :</label>
                <select name="employeId" class="form-select" required>
                    <option value="">-- Choisir un employé --</option>
                    <c:forEach var="emp" items="${employes}">
                        <option value="${emp.id}" ${fiche != null && fiche.employe.id == emp.id ? "selected" : ""}>
                                ${emp.nom} ${emp.prenom}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- Mois et Année -->
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label fw-semibold">Mois :</label>
                    <input type="number" name="mois" class="form-control" min="1" max="12"
                           value="${fiche.mois}" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label fw-semibold">Année :</label>
                    <input type="number" name="annee" class="form-control"
                           value="${fiche.annee != null ? fiche.annee : currentYear}" required>
                </div>
            </div>

            <!-- Salaire de base -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Salaire de base (€) :</label>
                <input type="number" step="0.01" name="salaireBase" class="form-control"
                       value="${fiche.salaireBase}" required>
            </div>

            <!-- Primes -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Primes (€) :</label>
                <input type="number" step="0.01" name="prime" class="form-control"
                       value="${fiche.prime}">
            </div>

            <!-- Déductions -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Déductions (€) :</label>
                <input type="number" step="0.01" name="deduction" class="form-control"
                       value="${fiche.deduction}">
            </div>

            <!-- Boutons -->
            <div class="text-center">
                <button type="submit" class="btn btn-primary px-4">
                    ${fiche != null ? "Mettre à jour" : "Créer"}
                </button>
                <a href="${pageContext.request.contextPath}/fiche" class="btn btn-secondary ms-2">Annuler</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
