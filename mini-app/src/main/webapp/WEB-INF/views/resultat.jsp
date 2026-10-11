<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.framework.Employe, mg.framework.Diplome" %>
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
    <%
        // objets créés et remplis par le framework, passés par le contrôleur
        Employe employe = (Employe) request.getAttribute("employe");
        Diplome diplome = (Diplome) request.getAttribute("diplome");
    %>
    <h1>Bonjour, <%= esc(employe.getPrenom()) %> <%= esc(employe.getNom()) %> !</h1>
    <p>Vous avez <%= employe.getAge() %> ans.</p>
    <p>Diplôme : <%= esc(diplome.getNom()) %> (<%= diplome.getAnnee() %>)</p>
    <p><a href="<%= request.getContextPath() %>/form">Retour au formulaire</a></p>
</body>
</html>