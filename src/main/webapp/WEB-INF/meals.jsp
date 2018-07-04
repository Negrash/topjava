<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal list</title>
    <style type="text/css">
        .red {
            background-color: red;
        }
        .green{
            background-color: green;
        }
    </style>
</head>

<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<p><a href="meals?action=insert&id=0">Add Meal</a></p>
<table border="1" cellspacing="0" cellpadding="5">
    <tr>
        <th>Id</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Exceed</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="meal" items="${mealList}">
        <tr class="${meal.exceed?'red':'green'}">
            <td>${meal.id}</td>
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.exceed}</td>
            <td><a href="meals?action=edit&id=${meal.id}">edit</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
