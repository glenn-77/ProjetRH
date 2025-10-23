<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Gestion des départements - Formulaire</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow-sm p-4">
        <h2 class="text-center mb-4">
            Créer un Département
        </h2>

        <form action="${pageContext.request.contextPath}/departement" method="post">
            <input type="hidden" name="id" value="${departement != null ? departement.id : ''}">

            <div class="mb-3">
                <label for="nom" class="form-label">Nom du département :</label>
                <input type="text" id="nom" name="nom" class="form-control"
                       value="${departement != null ? departement.nom : ''}" required>
            </div>

            <div class="mb-3">
                <label for="chefId" class="form-label">Chef de département :</label>
                <select id="chefId" name="chefId" class="form-select">
                    <option value="">-- Sélectionner un employé --</option>
                    <c:forEach var="emp" items="${employes}">
                        <option value="${emp.id}"
                            ${departement != null && departement.chef != null && departement.chef.id == emp.id ? "selected" : ""}>
                                ${emp.nom} ${emp.prenom} (${emp.poste})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="d-flex justify-content-center">
                <button type="submit" class="btn btn-primary px-4">
                    Créer
                </button>
                <a href="${pageContext.request.contextPath}/departement?action=list" class="btn btn-secondary ms-2">Annuler</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>

