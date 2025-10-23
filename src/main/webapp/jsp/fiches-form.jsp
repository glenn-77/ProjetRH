<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Gestion des fiches de paie - Formulaire</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow-sm p-4">
        <h2 class="text-center mb-4">Créer une Fiche de Paie</h2>

        <form action="${pageContext.request.contextPath}/fiche" method="post">
            <c:if test="${ficheDePaie != null}">
                <input type="hidden" name="id" value="${ficheDePaie.id}">
            </c:if>
            <div class="mb-3">
                <label for="employeId" class="form-label">Employé :</label>
                <select id="employeId" name="employeId" class="form-select" required>
                    <option value="">-- Sélectionner un employé --</option>
                    <c:forEach var="emp" items="${employes}">
                        <option value="${emp.id}">${emp.nom} ${emp.prenom} - ${emp.poste}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label for="mois" class="form-label">Mois :</label>
                <select id="mois" name="mois" class="form-select" required>
                    <c:forEach var="m" begin="1" end="12">
                        <option value="${m}">${m}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label for="annee" class="form-label">Année :</label>
                <input type="number" id="annee" name="annee" class="form-control"
                       value="${currentYear}" required>
            </div>

            <div class="mb-3">
                <label for="salaireBase" class="form-label">Salaire de base (€) :</label>
                <input type="number" step="0.01" id="salaireBase" name="salaireBase" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="prime" class="form-label">Prime (€) :</label>
                <input type="number" step="0.01" id="prime" name="prime" value="0.0" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="deduction" class="form-label">Déduction (€) :</label>
                <input type="number" step="0.01" id="deduction" name="deduction" value="0.0" class="form-control" required>
            </div>

            <div class="d-flex justify-content-center">
                <button type="submit" class="btn btn-success px-4">Générer</button>
                <a href="${pageContext.request.contextPath}/fiche" class="btn btn-secondary ms-2">Annuler</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
