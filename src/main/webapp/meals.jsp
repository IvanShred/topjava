<%@ page import="ru.javawebinar.topjava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="style.css">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Дата</th>
            <th>Описание</th>
            <th>Калории</th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr class=" <c:out value="${meal.excess ? 'excess' : 'norm'}" />">
                <td><%=DateUtil.dateTimeFormat(meal.getDateTime())%></td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
