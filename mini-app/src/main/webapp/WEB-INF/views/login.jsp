<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Connexion</title>
</head>
<body>
    <h1>Connexion</h1>
    
    <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;">${error}</p>
    <% } %>
    
    <form method="post" action="${pageContext.request.contextPath}/login">
        <div>
            <label for="username">Nom d'utilisateur :</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div>
            <label for="password">Mot de passe :</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div>
            <button type="submit">Se connecter</button>
        </div>
    </form>
    
    <p>Identifiants de test : admin / admin</p>
</body>
</html>
