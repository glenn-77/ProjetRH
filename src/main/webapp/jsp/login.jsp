<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Connexion</title></head>
<body>
<h3>Connexion</h3>
<form method="post" action="../auth">
    Login : <label>
    <input type="text" name="login">
</label><br>
    Mot de passe : <label>
    <input type="password" name="motDePasse">
</label><br>
    <button type="submit">Se connecter</button>
</form>
<p style="color:red">${error}</p>
</body>
</html>
