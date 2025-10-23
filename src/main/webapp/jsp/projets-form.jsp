<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Gestion des Projets - Formulaire</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow-sm p-4">
        <h2 class="text-center mb-4">
            ${projet != null ? "Modifier le projet" : "Créer un nouveau projet"}
        </h2>

        <form action="${pageContext.request.contextPath}/projet" method="post">
            <c:if test="${projet != null}">
                <input type="hidden" name="id" value="${projet.id}">
            </c:if>

            <div class="mb-3">
                <label for="nom" class="form-label">Nom du projet :</label>
                <input type="text" id="nom" name="nom" class="form-control"
                       value="${projet != null ? projet.nom : ''}" required>
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">Description :</label>
                <textarea id="description" name="description" class="form-control" rows="3" required>
                    ${projet != null ? projet.description : ''}
                </textarea>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="dateDebut" class="form-label">Date de début :</label>
                    <input type="date" id="dateDebut" name="dateDebut" class="form-control"
                           value="${projet != null ? projet.dateDebut : ''}" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="dateFin" class="form-label">Date de fin :</label>
                    <input type="date" id="dateFin" name="dateFin" class="form-control"
                           value="${projet != null ? projet.dateFin : ''}" required>
                </div>
            </div>

            <div class="mb-3">
                <label for="etat" class="form-label">Avancement du projet :</label>
                <select id="etat" name="etat" class="form-select" required>
                    <option value="EN_COURS" ${projet != null && projet.etat == 'EN_COURS' ? 'selected' : ''}>En cours</option>
                    <option value="TERMINE" ${projet != null && projet.etat == 'TERMINE' ? 'selected' : ''}>Terminé</option>
                    <option value="ANNULE" ${projet != null && projet.etat == 'ANNULE' ? 'selected' : ''}>Annulé</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="budget" class="form-label">Budget (€) :</label>
                <input type="number" step="0.01" id="budget" name="budget" class="form-control"
                       value="${projet != null ? projet.budget : ''}" required>
            </div>

            <div class="mb-3">
                <label for="chefId" class="form-label">Chef de projet :</label>
                <select id="chefId" name="chefId" class="form-select" required>
                    <option value="">-- Sélectionner un chef de projet --</option>
                    <c:forEach var="emp" items="${employes}">
                        <option value="${emp.id}"
                            ${projet != null && projet.chefProjet != null && projet.chefProjet.id == emp.id ? "selected" : ""}>
                                ${emp.nom} ${emp.prenom} (${emp.poste})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label for="employesAssignes" class="form-label">Employés assignés :</label>
                <select id="employesAssignes" name="employesAssignes" multiple class="form-select" size="5">
                    <c:forEach var="emp" items="${employes}">
                        <option value="${emp.id}"
                            ${projet != null && projet.employes != null && projet.employes.contains(emp) ? "selected" : ""}>
                                ${emp.nom} ${emp.prenom} (${emp.poste})
                        </option>
                    </c:forEach>
                </select>
                <div class="form-text">Maintenir Ctrl (Windows) ou Cmd (Mac) pour sélectionner plusieurs employés</div>
            </div>

            <div class="d-flex justify-content-center">
                <button type="submit" class="btn btn-primary px-4">
                    ${projet != null ? "Mettre à jour" : "Créer"}
                </button>
                <a href="${pageContext.request.contextPath}/projet?action=list" class="btn btn-secondary ms-2">Annuler</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
