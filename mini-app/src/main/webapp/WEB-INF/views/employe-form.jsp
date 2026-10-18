<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employé</title>
</head>
<body>
    <h1>Nouvel employé</h1>

    <%-- SPRINT 8 : enctype="multipart/form-data" obligatoire pour envoyer des fichiers.
         name="rectoCin" / name="versoCin" -> attributs MapFile de Employe --%>
    <form action="<%= request.getContextPath() %>/employe/save" method="post" enctype="multipart/form-data">
        <p><label>Nom : <input type="text" name="nom" required></label></p>
        <p><label>Prénom : <input type="text" name="prenom" required></label></p>
        <p><label>CIN recto : <input type="file" name="rectoCin" accept="image/*,application/pdf" required></label></p>
        <p><label>CIN verso : <input type="file" name="versoCin" accept="image/*,application/pdf" required></label></p>
        <button type="submit">Enregistrer</button>
    </form>
</body>
</html>
