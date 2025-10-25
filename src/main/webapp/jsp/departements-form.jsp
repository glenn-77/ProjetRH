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

            <div class="mb-3">
                <label class="form-label">Nom :</label>
                <input type="text" name="nom" class="form-control"
                       value="${departement != null ? departement.nom : ''}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Chef de département :</label>
                <select name="chefId" class="form-select">
                    <option value="">-- Sélectionner un employé --</option>
                    <c:forEach var="emp" items="${employes}">
                        <option value="${emp.id}"
                            ${departement != null && departement.chef != null && departement.chef.id == emp.id ? "selected" : ""}>
                                ${emp.nom} ${emp.prenom} (${emp.poste})
                        </option>
                    </c:forEach>
                </select>
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
