<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    // échappe le texte saisi par l'utilisateur avant de l'afficher (évite l'injection HTML)
    private static String esc(Object o) {
        if (o == null) return "";
        return o.toString()
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Résultat</title>
</head>
<body>
    <h1>Bonjour, <%= esc(request.getAttribute("nom")) %> !</h1>
    <p>Vous avez <%= esc(request.getAttribute("age")) %> ans.</p>
    <p><a href="<%= request.getContextPath() %>/form">Retour au formulaire</a></p>
</body>
</html>