<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Formulaire Département</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<jsp:include page="navbar.jsp" />

<div class="container mt-5">
    <div class="card shadow-sm p-4 mx-auto" style="max-width:600px;">
        <h2 class="text-center mb-4">${departement != null ? "Modifier un Département" : "Créer un Département"}</h2>

        <form action="${pageContext.request.contextPath}/departement" method="post">
            <c:if test="${departement != null}">
                <input type="hidden" name="id" value="${departement.id}">
            </c:if>

            <c:if test="${role == 'ADMINISTRATEUR'}">
            <div class="mb-3">
                <label class="form-label">Nom :</label>
                <input type="text" name="nom" class="form-control"
                       value="${departement != null ? departement.nom : ''}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Chef de département :</label>
                <select name="chefId" class="form-select">
                    <option value="">-- Sélectionner un chef de département --</option>
                    <c:forEach var="emp" items="${chefs}">
                        <option value="${emp.id}"
                            ${departement != null && departement.chef != null && departement.chef.id == emp.id ? "selected" : ""}>
                                ${emp.nom} ${emp.prenom} (${emp.poste})
                        </option>
                    </c:forEach>
                </select>
            </div>
            </c:if>


            <c:if test="${not (role == 'ADMINISTRATEUR')}">
                <div class="mb-3">
                    <label class="form-label">Nom: </label>
                    <p class="form-control">${departement.nom}</p>
                </div>

                <div class="mb-3">
                    <label class="form-label">Chef de département: </label>
                    <p class="form-control">${departement.chef.nom} ${departement.chef.prenom} (${departement.chef.poste})</p>
                </div>
            </c:if>

            <!-- Liste des employés du département -->
            <div class="mb-3">
                <label class="form-label">Employés du département :</label>
                <div class="border p-3 rounded" style="max-height: 250px; overflow-y: auto;">
                    <c:forEach var="e" items="${employes}">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="employes"
                                   value="${e.id}" id="emp${e.id}"
                            <c:if test="${departement != null && departement.employes != null}">
                            <c:forEach var="emp" items="${departement.employes}">
                                   <c:if test="${emp.id == e.id}">checked</c:if>
                            </c:forEach>
                            </c:if>>
                            <label class="form-check-label" for="emp${e.id}">
                                    ${e.prenom} ${e.nom} <small class="text-muted">(${e.poste})</small>
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div class="text-center">
                <button type="submit" class="btn btn-primary px-4">
                    ${departement != null ? "Mettre à jour" : "Créer"}
                </button>
                <a href="${pageContext.request.contextPath}/departement?action=list" class="btn btn-secondary ms-2">Annuler</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
