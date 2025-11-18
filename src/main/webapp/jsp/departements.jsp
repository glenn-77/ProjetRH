<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des départements</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<jsp:include page="navbar.jsp" />
<div class="container mt-5">
    <h2 class="text-center mb-4">Gestion des départements</h2>

    <!-- Bouton d'ajout -->
    <c:if test="${role == 'ADMINISTRATEUR'}">
        <div class="d-flex justify-content-end mb-3">
            <a href="${pageContext.request.contextPath}/departement?action=add" class="btn btn-success">
                + Ajouter un département
            </a>
        </div>
    </c:if>

    <!-- Tableau principal -->
    <table class="table table-bordered table-hover align-middle shadow-sm">
        <thead class="table-dark text-center">
        <tr>
            <th>#</th>
            <th>Nom du département</th>
            <th>Chef du département</th>
            <th>Employés</th>
            <c:if test="${role == 'ADMINISTRATEUR' or role == 'CHEF_DE_DEPARTEMENT'}">
            <th>Actions</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty departements}">
                <c:forEach var="d" items="${departements}">
                    <tr>
                        <td class="text-center">${d.id}</td>
                        <td>${d.nom}</td>

                        <!-- Chef -->
                        <td>
                            <c:choose>
                                <c:when test="${d.chef != null}">
                                     ${d.chef.prenom} ${d.chef.nom}
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted fst-italic">Aucun</span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <!-- Liste des employés -->
                        <td>
                            <c:choose>
                                <c:when test="${not empty d.employes}">
                                    <ul class="mb-0 ps-3">
                                        <c:forEach var="e" items="${d.employes}">
                                            <li>${e.prenom} ${e.nom} <small class="text-muted">(${e.poste})</small></li>
                                        </c:forEach>
                                    </ul>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted fst-italic">Aucun employé</span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <!-- Actions -->
                        <c:if test="${role == 'ADMINISTRATEUR' or role == 'CHEF_DE_DEPARTEMENT'}">
                            <td class="text-center">
                                <a href="${pageContext.request.contextPath}/departement?action=edit&id=${d.id}"
                                class="btn btn-warning btn-sm me-2">Modifier</a>
                        </c:if>
                        <c:if test="${role == 'ADMINISTRATEUR'}">
                                <a href="${pageContext.request.contextPath}/departement?action=delete&id=${d.id}"
                                class="btn btn-danger btn-sm"
                                   onclick="return confirm('Supprimer ce département ?');">Supprimer</a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <tr>
                    <td colspan="5" class="text-center text-muted fst-italic py-4">
                        Aucun département trouvé.
                    </td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>

</body>
</html>
