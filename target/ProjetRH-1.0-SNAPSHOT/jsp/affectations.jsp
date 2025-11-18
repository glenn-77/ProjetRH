<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Affecter un employé à des projets</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow-sm p-4">
        <h3 class="text-center mb-4">
            Affecter ${employe.nom} ${employe.prenom} à des projets
        </h3>

        <form action="${pageContext.request.contextPath}/employe" method="post">
            <input type="hidden" name="formType" value="affecter">
            <input type="hidden" name="id" value="${employe.id}">

            <div class="mb-3">
                <label class="form-label">Projets disponibles :</label>
                <div class="form-check">
                    <c:forEach var="p" items="${projets}">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="projets" value="${p.id}"
                                   id="projet${p.id}"
                            <c:if test="${employe.projets != null}">
                            <c:forEach var="ep" items="${employe.projets}">
                                   <c:if test="${ep.id == p.id}">checked</c:if>
                            </c:forEach>
                            </c:if>>
                            <label class="form-check-label" for="projet${p.id}">
                                    ${p.nom} — <small class="text-muted">${p.description}</small>
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div class="d-flex justify-content-center">
                <button type="submit" class="btn btn-primary me-2">Enregistrer</button>
                <a href="${pageContext.request.contextPath}/employe?action=list" class="btn btn-secondary">Retour</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>

