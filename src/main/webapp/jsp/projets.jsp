<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Projets</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2 class="m-0">Liste des projets</h2>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/projet?action=add">
            + Nouveau projet
        </a>
    </div>

    <c:choose>
        <c:when test="${empty projets}">
            <div class="alert alert-info">Aucun projet pour le moment.</div>
        </c:when>
        <c:otherwise>
            <div class="card shadow-sm">
                <div class="table-responsive">
                    <table class="table table-striped align-middle mb-0">
                        <thead class="table-light">
                        <tr>
                            <th>Nom</th>
                            <th>Description</th>
                            <th>Période</th>
                            <th>État</th>
                            <th>Budget (€)</th>
                            <th>Chef de projet</th>
                            <th>Employés assignés</th>
                            <th class="text-center" style="width: 140px;">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="p" items="${projets}">
                            <tr>
                                <td class="fw-semibold">${p.nom}</td>
                                <td>${p.description}</td>
                                <td>
                                    <div>
                                        <small class="text-muted">Début :</small>
                                        <span>
                                            <c:choose>
                                                <c:when test="${p.dateDebut != null}">
                                                    <fmt:formatDate value="${p.dateDebut}" pattern="yyyy-MM-dd"/>
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </span>
                                    </div>
                                    <div>
                                        <small class="text-muted">Fin :</small>
                                        <span>
                                            <c:choose>
                                                <c:when test="${p.dateFin != null}">
                                                    <fmt:formatDate value="${p.dateFin}" pattern="yyyy-MM-dd"/>
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </span>
                                    </div>
                                </td>
                                <td>
                                    <span class="badge bg-${p.etat == 'EN_COURS' ? 'warning' : (p.etat == 'TERMINE' ? 'success' : 'secondary')}">
                                            ${p.etat}
                                    </span>
                                </td>
                                <td>
                                    <c:if test="${p.budget != null}">
                                        <fmt:formatNumber value="${p.budget}" type="number" minFractionDigits="2" maxFractionDigits="2"/>
                                    </c:if>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${p.chefProjet != null}">
                                            ${p.chefProjet.nom} ${p.chefProjet.prenom}
                                            <c:if test="${not empty p.chefProjet.poste}">
                                                <small class="text-muted d-block">${p.chefProjet.poste}</small>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>-</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty p.employes}">
                                            <ul class="m-0 ps-3">
                                                <c:forEach var="e" items="${p.employes}">
                                                    <li>${e.nom} ${e.prenom}</li>
                                                </c:forEach>
                                            </ul>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted">Aucun</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-center">
                                    <a class="btn btn-sm btn-outline-primary me-1"
                                       href="${pageContext.request.contextPath}/projet?action=edit&id=${p.id}">
                                        Éditer
                                    </a><br/>
                                    <a class="btn btn-sm btn-outline-danger"
                                       href="${pageContext.request.contextPath}/projet?action=delete&id=${p.id}"
                                       onclick="return confirm('Supprimer ce projet ?');">
                                        Supprimer
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

</div>

</body>
</html>
