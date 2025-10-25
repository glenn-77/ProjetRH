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
        <h2 class="text-center mb-4">${fiche != null ? "Modifier une Fiche" : "Créer une Fiche"}</h2>

        <form action="${pageContext.request.contextPath}/fiche" method="post">
            <c:if test="${fiche != null}">
                <input type="hidden" name="id" value="${fiche.id}">
            </c:if>

            <div class="mb-3">
                <label class="form-label">Employé :</label>
                <select name="employeId" class="form-select" required>
                    <option value="">-- Choisir un employé --</option>
                    <c:forEach var="emp" items="${employes}">
                        <option value="${emp.id}" ${fiche != null && fiche.employe.id == emp.id ? "selected" : ""}>
                                ${emp.nom} ${emp.prenom}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Mois</label>
                    <input type="number" name="mois" class="form-control" min="1" max="12" value="${fiche.mois}" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Année</label>
                    <input type="number" name="annee" class="form-control" value="${fiche.annee}" required>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">Primes (€)</label>
                <input type="number" step="0.01" name="primes" class="form-control" value="${fiche.primes}">
            </div>

            <div class="mb-3">
                <label class="form-label">Déductions (€)</label>
                <input type="number" step="0.01" name="retenues" class="form-control" value="${fiche.retenues}">
            </div>

            <div class="text-center">
                <button type="submit" class="btn btn-primary px-4">${fiche != null ? "Mettre à jour" : "Créer"}</button>
                <a href="${pageContext.request.contextPath}/fiche" class="btn btn-secondary ms-2">Annuler</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
