<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.framework.Employe, mg.itu.frame.MapFile, java.util.Base64" %>
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

    // infos du fichier + aperçu si c'est une image
    private static String afficher(MapFile f) {
        if (f == null) return "<em>aucun fichier</em>";
        String html = esc(f.getNomComplet()) + " &mdash; extension : " + esc(f.getExtension())
                + " &mdash; " + f.getTaille() + " octets";
        String ext = f.getExtension() == null ? "" : f.getExtension().toLowerCase();
        if (ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("gif")) {
            String mime = ext.equals("jpg") ? "image/jpeg" : "image/" + ext;
            html += "<br><img style=\"max-width:300px\" src=\"data:" + mime + ";base64,"
                    + Base64.getEncoder().encodeToString(f.getBinaire()) + "\">";
        }
        return html;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employé enregistré</title>
</head>
<body>
    <% Employe employe = (Employe) request.getAttribute("employe"); %>
    <h1><%= esc(employe.getPrenom()) %> <%= esc(employe.getNom()) %></h1>
    <p><strong>CIN recto :</strong> <%= afficher(employe.getRectoCin()) %></p>
    <p><strong>CIN verso :</strong> <%= afficher(employe.getVersoCin()) %></p>
    <p><a href="<%= request.getContextPath() %>/employe">Retour au formulaire</a></p>
</body>
</html>
