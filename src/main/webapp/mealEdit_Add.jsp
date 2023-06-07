<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<html>
<head>
  <title>Add new meal</title>
</head>
<body>


<form method="POST" action='MealServlet' name="frmAddUser">

  Meal ID : <input type="text" readonly="readonly" name="mealId"
                   value="<c:out value="${meal.id}" />" /> <br />

  Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />" /> <br />
  DateTime : <input
        type="datetime-local" name="mealdateTime"
        value="<c: out value="${meal.dateTime}" />" /> <br />
  Calories : <input type="text" name="calories"
                 value="<c:out value="${meal.calories}" />" /> <br /> <input
        type="submit" value="Submit" />
</form>
</body>
</html>