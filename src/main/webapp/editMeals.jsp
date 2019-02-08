<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Meal</title>
</head>
<body>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${meal.id}">
        <h1>Дата:</h1>
        <dl>
            <input type="datetime-local" name="data" value="${meal.dateTime}">
        </dl>
        <h1>Блюдо:</h1>
        <dl>
            <input type="text" name="description" size=55 value="${meal.description}">
        </dl>
        <h1>Калории:</h1>
        <dl>
            <input type="number" name="calories" value="${meal.calories}">
        </dl>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back(); return false;">Отменить</button>
    </form>
</section>
</body>
</html>
