<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
    <h1>Dashboard</h1>
    
    <p>Bienvenue, ${username}!</p>
    
    <p>Vous êtes authentifié avec succès.</p>
    
    <a href="${pageContext.request.contextPath}/logout">Se déconnecter</a>
</body>
</html>
