<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 600px; margin: 50px auto; padding: 20px; }
        .welcome { color: #007bff; margin-bottom: 20px; }
        button { padding: 10px 20px; background: #dc3545; color: white; border: none; cursor: pointer; }
        button:hover { background: #c82333; }
    </style>
</head>
<body>
    <h1>Dashboard</h1>
    
    <p class="welcome">Bienvenue, ${username}!</p>
    
    <p>Vous êtes connecté. La session est gérée via MySession.</p>
    
    <form method="GET" action="logout">
        <button type="submit">Se déconnecter</button>
    </form>
</body>
</html>
