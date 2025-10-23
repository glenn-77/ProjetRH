<%@ page contentType="text/html;charset=UTF-8" import="java.util.*,com.example.model.Departement" %>
<html>
<head><title>Départements</title></head>
<body>
<h2>Liste des Départements</h2>
<a href="departement?action=add">Ajouter un Département</a>
<table border="1">
    <tr><th>ID</th><th>Nom</th><th>Chef de Département</th><th>Actions</th></tr>
    <%
        List<Departement> depts = (List<Departement>) request.getAttribute("departements");
        for (Departement d : depts) {
    %>
    <tr>
        <td><%=d.getId()%></td>
        <td><%=d.getNom()%></td>
        <td><%=d.getChef().getNom()%> <%=d.getChef().getPrenom()%></td>
        <td><a href="departement?action=delete&id=<%=d.getId()%>">Supprimer</a></td>
    </tr>
    <% } %>
</table>
</body>
</html>
