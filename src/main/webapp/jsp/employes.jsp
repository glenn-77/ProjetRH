<%@ page contentType="text/html;charset=UTF-8" import="java.util.*,com.example.model.Employe" %>
<html>
<head><title>Liste des employés</title></head>
<body>
<h2>Liste des employés</h2>
<a href="employe?action=add">Ajouter un employé</a>
<table border="1">
    <tr><th>ID</th><th>Nom</th><th>Prénom</th><th>Département</th><th>Actions</th></tr>
    <%
        List<Employe> employes = (List<Employe>) request.getAttribute("employes");
        for (Employe e : employes) {
    %>
    <tr>
        <td><%=e.getId()%></td>
        <td><%=e.getNom()%></td>
        <td><%=e.getPrenom()%></td>
        <td><%= (e.getDepartement()!=null ? e.getDepartement().getNom() : "—") %></td>
        <td>
            <a href="employe?action=edit&id=<%=e.getId()%>">Modifier</a> |
            <a href="employe?action=delete&id=<%=e.getId()%>">Supprimer</a>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>