<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire</title>
</head>
<body>
    <h1>Formulaire</h1>

    <%-- save(Employe employe, Diplome diplome) :
         name="employe.xxx" -> attribut xxx de employe, name="diplome.xxx" -> attribut xxx de diplome --%>
    <form action="<%= request.getContextPath() %>/save" method="post">
        <h2>Employé</h2>
        <p><label>Nom : <input type="text" name="employe.nom" required></label></p>
        <p><label>Prénom : <input type="text" name="employe.prenom" required></label></p>
        <p><label>Âge : <input type="number" name="employe.age" min="0" required></label></p>

        <h2>Diplôme</h2>
        <p><label>Nom : <input type="text" name="diplome.nom" required></label></p>
        <p><label>Année : <input type="number" name="diplome.annee" min="1900" required></label></p>

        <button type="submit">Save</button>
    </form>
</body>
</html>
