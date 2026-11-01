<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin</title>
</head>
<body>
    <h1>Admin Panel</h1>
    
    <p>Bienvenue, ${username}!</p>
    
    <p>Vous êtes connecté en tant qu'administrateur.</p>
    
    <p>Cette page est protégée par le rôle "admin".</p>
    
    <a href="${pageContext.request.contextPath}/dashboard">Retour au Dashboard</a>
    <br>
    <a href="${pageContext.request.contextPath}/logout">Se déconnecter</a>
</body>
</html>
