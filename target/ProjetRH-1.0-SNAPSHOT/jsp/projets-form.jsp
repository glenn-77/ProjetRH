<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Formulaire Projet</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<jsp:include page="navbar.jsp" />

<div class="container mt-5">
    <div class="card shadow-sm p-4 mx-auto" style="max-width:600px;">
        <h2 class="text-center mb-4">${projet != null ? "Modifier un Projet" : "Créer un Projet"}</h2>

        <form action="${pageContext.request.contextPath}/projet" method="post">
            <c:if test="${projet != null}">
                <input type="hidden" name="id" value="${projet.id}">
            </c:if>

            <c:if test="${role == 'ADMINISTRATEUR' || role == 'CHEF_DE_DEPARTEMENT'}">
            <div class="mb-3">
                <label class="form-label">Nom du projet</label>
                <input type="text" name="nom" class="form-control" value="${projet.nom}" required>
            </div>
            </c:if>

            <c:if test="${not (role == 'ADMINISTRATEUR' || role == 'CHEF_DE_DEPARTEMENT')}">
                <div class="mb-3">
                    <label class="form-label">Nom du projet</label>
                    <p class="form-control">${projet.nom}</p>
                </div>
            </c:if>

            <c:if test="${role == 'ADMINISTRATEUR'}">
            <div class="mb-3">
                <label for="departementSelect" class="form-label">Département :</label>
                <select id="departementSelect" name="depId" class="form-select">
                    <option value="">-- Sélectionner un département --</option>
                    <c:forEach var="dep" items="${departements}">
                        <option value="${dep.id}"
                            ${projet != null && projet.departement != null && projet.departement.id == dep.id ? "selected" : ""}>
                                ${dep.nom}
                        </option>
                    </c:forEach>
                </select>
            </div>
            </c:if>

            <c:if test="${not (role == 'ADMINISTRATEUR')}">
            <div class="mb-3">
                <label class="form-label">Département</label>
                <p class="form-control">${user.employe.departement.nom}</p>
            </div>
            </c:if>

            <c:if test="${role == 'ADMINISTRATEUR' || role == 'CHEF_DE_DEPARTEMENT'}">
            <div class="mb-3">
                <label class="form-label">Description</label>
                <textarea name="description" class="form-control" rows="4" required>${projet.description}</textarea>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Date début</label>
                    <input type="date" name="dateDebut" class="form-control" value="${projet.dateDebut}" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Date fin</label>
                    <input type="date" name="dateFin" class="form-control" value="${projet.dateFin}" required>
                </div>
            </div>
            </c:if>

            <c:if test="${not (role == 'ADMINISTRATEUR' || role == 'CHEF_DE_DEPARTEMENT')}">
                <div class="mb-3">
                    <label class="form-label">Description</label>
                    <p class="form-control">${projet.description}</p>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Date début</label>
                        <p class="form-control">${projet.dateDebut}</p>
                    </div>

                    <div class="col-md-6 mb-3">
                        <label class="form-label">Date fin</label>
                        <p class="form-control">${projet.dateFin}</p>
                    </div>
                </div>
            </c:if>

            <div class="mb-3">
                <label for="etat" class="form-label">Avancement du projet :</label>
                <select id="etat" name="etat" class="form-select" required>
                    <option value="EN_COURS" ${projet != null && projet.etat == 'EN_COURS' ? 'selected' : ''}>En cours</option>
                    <option value="TERMINE" ${projet != null && projet.etat == 'TERMINE' ? 'selected' : ''}>Terminé</option>
                    <option value="ANNULE" ${projet != null && projet.etat == 'ANNULE' ? 'selected' : ''}>Annulé</option>
                </select>
            </div>

            <c:if test="${role == 'ADMINISTRATEUR' || role == 'CHEF_DE_DEPARTEMENT'}">
                <div class="mb-3">
                    <label for="budget" class="form-label">Budget (€) :</label>
                    <input type="number" step="0.01" id="budget" name="budget" class="form-control"
                       value="${projet != null ? projet.budget : ''}" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Chef de projet :</label>
                    <select id="chefSelect" name="chefId" class="form-select">
                        <option value="">-- Sélectionner un chef de projet --</option>
                    </select>
                </div>
            </c:if>

            <c:if test="${not (role == 'ADMINISTRATEUR' || role == 'CHEF_DE_DEPARTEMENT')}">
                <div class="mb-3">
                    <label class="form-label">Budget (€)</label>
                    <p class="form-control">${projet.budget}</p>
                </div>

                <div class="mb-3">
                    <label class="form-label">Chef de projet</label>
                    <p class="form-control">${projet.chefProjet.prenom} ${projet.chefProjet.nom} (${projet.chefProjet.poste})</p>
                </div>
            </c:if>

            <div class="mb-3">
                <label class="form-label">Employés assignés :</label>
                <div class="border rounded p-3" id="employeSelect" style="max-height: 250px; overflow-y: auto;">
                </div>
            </div>

            <div class="text-center">
                <button type="submit" class="btn btn-primary px-4">${projet != null ? "Mettre à jour" : "Créer"}</button>
                <a href="${pageContext.request.contextPath}/projet?action=list" class="btn btn-secondary ms-2">Annuler</a>
            </div>
        </form>

        <script>
            const assignedEmployees = [
                <c:forEach var="emp" items="${projet.employes}">
                "${emp.id}",
                </c:forEach>
            ];
        </script>

        <c:if test="${role != 'EMPLOYE'}">
            <script>
                const depSelect = document.getElementById("departementSelect");
                const empContainer = document.getElementById("employeSelect");

                function loadEmployees(depId) {
                    empContainer.innerHTML = "";

                    fetch('employe?action=listByDepartement&id=' + depId)
                        .then(res => res.json())
                        .then(data => {
                            data.forEach(emp => {
                                let isChecked = assignedEmployees.includes(String(emp.id)) ? "checked" : "";
                                let div = document.createElement("div");
                                div.className = "form-check";

                                div.innerHTML =
                                    '<input type="checkbox" class="form-check-input" name="employesAssignes" value="' + emp.id + '" ' + isChecked + '>' +
                                    '<label class="form-check-label">' + emp.prenom + ' ' + emp.nom + ' (' + emp.poste + ')</label>';

                                empContainer.appendChild(div);
                            });
                        });
                }

                // Admin : changement de département
                if (depSelect) {
                    depSelect.addEventListener("change", () => {
                        if (depSelect.value) loadEmployees(depSelect.value);
                    });
                    if (depSelect.value) loadEmployees(depSelect.value); // mode edit
                } else {
                    // Chef projet / chef dep (département fixe)
                    let fixedDep = "${user.employe.departement.id}";
                    loadEmployees(fixedDep);
                }
            </script>
        </c:if>

        <c:if test="${role == 'ADMINISTRATEUR' || role == 'CHEF_DE_DEPARTEMENT'}">
            <script>
                const chefSelect = document.getElementById("chefSelect");
                const depSelect2 = document.getElementById("departementSelect");

                function loadChefSelect(depId) {
                    chefSelect.innerHTML = '<option value="">-- Sélectionner un chef de projet --</option>';

                    fetch('employe?action=listChefsByDepartement&id=' + depId)
                        .then(res => res.json())
                        .then(data => {
                            data.forEach(emp => {
                                let opt = document.createElement("option");
                                opt.value = emp.id;
                                opt.textContent = emp.prenom + ' ' + emp.nom + ' (' + emp.poste + ')';
                                chefSelect.appendChild(opt);
                            });

                            const currentChef = "${projet != null && projet.chefProjet != null ? projet.chefProjet.id : ""}";
                            if (currentChef) chefSelect.value = currentChef;
                        });
                }

                // ADMIN → select visible
                if (depSelect2) {
                    depSelect2.addEventListener("change", () => loadChefSelect(depSelect2.value));
                    if (depSelect2.value) loadChefSelect(depSelect2.value);
                }
                // CHEF_DE_DEPARTEMENT
                else {
                    const depIdFixed = "${user.employe.departement.id}";
                    loadChefSelect(depIdFixed);
                }
            </script>
        </c:if>
    </div>
</div>
<!-- FOOTER -->
<footer>
    &copy; 2025 - Système de Gestion RH
</footer>
</body>
</html>
