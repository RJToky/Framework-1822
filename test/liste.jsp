<%@ page import="model.Emp" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
  </head>
  <body>
    <h1>Liste employe</h1>
    <% if(request.getAttribute("allEmp") != null) { %>
      <% Emp[] allEmp = (Emp[]) request.getAttribute("allEmp"); %>
      <% for(int i = 0; i < allEmp.length; i++) { %>
        <li><%= allEmp[i].getNom() + " " + allEmp[i].getAge() %></li>
      <% } %>
    <% } else { out.println("Aucun resultat"); } %>
  </body>
</html>
