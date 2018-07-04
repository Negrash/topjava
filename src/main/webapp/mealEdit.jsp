<<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal edit</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal edit</h2>

<form method="post" action="meals" name="frmEditMeal">
    Id : <input type="text" readonly="readonly" name="id"
                value="${meal.id}"> <br>
    DateTime : <input type="datetime-local" name="dateTime"
                      value="${meal.dateTime}"> <br>
    Description : <input type="text" name="description"
                         value="${meal.description}"> <br>
    Calories : <input type="text" name="calories"
                      value="${meal.calories}"> <br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
