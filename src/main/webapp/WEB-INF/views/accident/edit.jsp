<%--
  Created by IntelliJ IDEA.
  User: gurov
  Date: 15.03.2022
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
      crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
<head>
    <title>Редактирование инцидента</title>
</head>
<body>
<form action="<c:url value='/save'/>" method='POST'>
    <input type='text' hidden name='id' value="${accident.id}">
    <table>
        <tr>
            <td>Название:</td>
            <td><input type='text' name='name' value="${accident.name}"></td>
        </tr>
        <tr>
            <td>Описание:</td>
            <td><input type='text' name='text' value="${accident.text}"></td>
        </tr>
        <tr>
            <td>Адрес:</td>
            <td><input type='text' name='address' value="${accident.address}"></td>
        </tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit" value="Сохранить" /></td>
        </tr>
    </table>
</body>
</html>
