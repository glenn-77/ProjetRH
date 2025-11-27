<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Formulaire Employé</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<jsp:include page="navbar.jsp" />

<div class="container mt-5">
    <div class="card shadow-sm p-4 mx-auto" style="max-width:700px;">
        <h2 class="text-center mb-4">${employe != null ? "Modifier un Employé" : "Ajouter un Employé"}</h2>

        <form action="${pageContext.request.contextPath}/employe" method="post">
            <c:if test="${employe != null}">
                <input type="hidden" name="id" value="${employe.id}">
            </c:if>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Nom</label>
                    <input type="text" name="nom" class="form-control" value="${employe.nom}" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Prénom</label>
                    <input type="text" name="prenom" class="form-control" value="${employe.prenom}" required>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" value="${employe.email}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Adresse</label>
                <input type="text" name="adresse" class="form-control" value="${employe.adresse}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Téléphone</label>
                <input type="text" name="telephone" class="form-control" value="${employe.telephone}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Poste</label>
                <input type="text" name="poste" class="form-control" value="${employe.poste}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Grade</label>
                <select name="grade" class="form-select" required>
                    <c:forEach var="g" items="${['JUNIOR','INTERMEDIAIRE','SENIOR','STAGIAIRE','MANAGER','DIRECTEUR']}">
                        <option value="${g}" ${employe != null && employe.grade == g ? 'selected' : ''}>${g}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Salaire (€)</label>
                <input type="number" step="0.01" name="salaireBase" class="form-control" value="${employe.salaireBase}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Département</label>
                <select name="departementId" class="form-select" required>
                    <option value="">-- Choisir un département --</option>
                    <c:forEach var="dep" items="${departements}">
                        <option value="${dep.id}" ${employe != null && employe.departement.id == dep.id ? 'selected' : ''}>${dep.nom}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Rôle</label>
                <select name="roleId" class="form-select" required>
                    <option value="">-- Choisir un rôle --</option>
                    <c:forEach var="r" items="${roles}">
                        <option value="${r.id}"
                            ${user != null && user.role != null && user.role.id == r.id ? 'selected' : ''}>
                                ${r.nomRole.name()}
                        </option>

                    </c:forEach>
                </select>
            </div>

            <div class="text-center">
                <button type="submit" class="btn btn-primary px-4">
                    ${employe != null ? "Mettre à jour" : "Ajouter"}
                </button>
                <a href="${pageContext.request.contextPath}/employe" class="btn btn-secondary ms-2">Annuler</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
