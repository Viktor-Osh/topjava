<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<%@ page contentType="text/html;charset=UTF-8" %>


<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
    <TABLE cellspacing="0" border="1" cellpadding="5" width="500">
        <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th colspan=2>Action</th>
        </tr>
        <c:forEach var="meal" items="${meals}">
            <c:set var="color" value="${meal.excess ? 'red' : 'green'}" />
            <tr style="color:${color}">
                    <td>${meal.id}</td>
                    <td>${f:formatLocalDateTime(meal.dateTime, 'yyyy-dd-MM HH:mm')}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                <td><a href="meals?action=edit&mealId=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&mealId=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </TABLE>
<p><a href="mealServlet?action=insert">Add User</a></p>
</body>
</html>
