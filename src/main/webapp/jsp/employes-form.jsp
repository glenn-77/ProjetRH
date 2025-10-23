<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Employés - Formulaire</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="text-center mb-4">${employe != null ? "Modifier un employé" : "Ajouter un employé"}</h2>

    <form action="${pageContext.request.contextPath}/employe" method="post" class="card p-4 shadow-sm">
        <!-- Champ caché pour l'ID (utilisé uniquement si on modifie un employé existant) -->
        <c:if test="${employe != null}">
            <input type="hidden" name="id" value="${employe.id}">
        </c:if>

        <div class="mb-3">
            <label class="form-label">Nom</label>
            <label>
                <input type="text" class="form-control" name="nom" value="${employe.nom}" required>
            </label>
        </div>

        <div class="mb-3">
            <label class="form-label">Prénom</label>
            <label>
                <input type="text" class="form-control" name="prenom" value="${employe.prenom}" required>
            </label>
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <label>
                <input type="email" class="form-control" name="email" value="${employe.email}" required>
            </label>
        </div>

        <div class="mb-3">
            <label class="form-label">Adresse</label>
            <label>
                <input type="text" class="form-control" name="adresse" value="${employe.adresse}" required>
            </label>
        </div>

        <div class="mb-3">
            <label class="form-label">Telephone</label>
            <label>
                <input type="text" class="form-control" name="telephone" value="${employe.telephone}" required>
            </label>
        </div>

        <div class="mb-3">
            <label class="form-label">Poste</label>
            <label>
                <input type="text" class="form-control" name="poste" value="${employe.poste}" required>
            </label>
        </div>

        <div class="form-group">
            <label for="grade">Grade :</label>
            <select id="grade" name="grade" class="form-control" required>
                <option value="JUNIOR" ${employe != null && employe.grade == 'JUNIOR' ? 'selected' : ''}>Junior</option>
                <option value="INTERMEDIAIRE" ${employe != null && employe.grade == 'INTERMEDIAIRE' ? 'selected' : ''}>Intermédiaire</option>
                <option value="SENIOR" ${employe != null && employe.grade == 'SENIOR' ? 'selected' : ''}>Senior</option>
                <option value="STAGIAIRE" ${employe != null && employe.grade == 'STAGIAIRE' ? 'selected' : ''}>Stagiaire</option>
                <option value="MANAGER" ${employe != null && employe.grade == 'MANAGER' ? 'selected' : ''}>Manager</option>
                <option value="DIRECTEUR" ${employe != null && employe.grade == 'DIRECTEUR' ? 'selected' : ''}>Directeur</option>
            </select>
        </div><br/>


        <div class="mb-3">
            <label class="form-label">Salaire (€)</label>
            <label>
                <input type="number" step="0.01" class="form-control" name="salaireBase" value="${employe.salaireBase}" required>
            </label>
        </div>

        <div class="mb-3">
            <label class="form-label">Département</label>
            <label>
                <select class="form-select" name="departementId" required>
                    <option value="">-- Choisir un département --</option>
                    <c:forEach var="dep" items="${departements}">
                        <option value="${dep.id}" ${employe != null && employe.departement.id == dep.id ? "selected" : ""}>
                                ${dep.nom}
                        </option>
                    </c:forEach>
                </select>
            </label>
        </div>

        <div class="text-center mt-4">
            <button type="submit" class="btn btn-primary px-4">
                ${employe != null ? "Mettre à jour" : "Ajouter"}
            </button>
            <a href="${pageContext.request.contextPath}/employe" class="btn btn-secondary px-4 ms-2">Annuler</a>
        </div>
    </form>
</div>

</body>
</html>
