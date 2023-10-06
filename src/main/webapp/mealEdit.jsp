<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Add new meal</title>
</head>
<body>
<form method="POST" action='meals' name="addMeal">
    <c:choose>
        <c:when test="${not empty param.mealId}">
            <h2>Edit meal</h2>
            <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
            <input type="hidden" name="mealId" value="<c:out value="${meal.id}" />" />
            Description : <input
                type="text" name="description"
                value="<c:out value="${meal.description}" />"/> <br/>
            DateTime : <input
                type="datetime-local" name="dateTime"
                value="<c:out value="${meal.dateTime}" />"/> <br/>
            Calories : <input type="text" name="calories"
                              value="<c:out value="${meal.calories}" />"/> <br/>
            <input type="submit" value="Submit"/> <button onclick="window.history.back()" type="button">Cancel</button>
        </c:when>
        <c:otherwise>
            <h2>Add meal</h2>
            Description : <input
                type="text" name="description"/><br/>
            DateTime : <input
                type="datetime-local" name="dateTime"/><br/>
            Calories : <input type="text" name="calories"/> <br/>
            <input type="submit" value="Submit"/> <button onclick="window.history.back()" type="button">Cancel</button>
        </c:otherwise>
    </c:choose>
</form>
</body>
</html>