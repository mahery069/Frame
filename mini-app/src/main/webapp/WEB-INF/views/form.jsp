<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire</title>
</head>
<body>
    <h1>Formulaire</h1>

    <%-- action = URL de la route @RequestMapping(url = "/save", method = "POST") --%>
    <form action="<%= request.getContextPath() %>/save" method="post">
        <p>
            <label>Nom :
                <input type="text" name="nom" required>
            </label>
        </p>
        <p>
            <label>Âge :
                <input type="number" name="age" min="0" required>
            </label>
        </p>
        <button type="submit">Save</button>
    </form>
</body>
</html>