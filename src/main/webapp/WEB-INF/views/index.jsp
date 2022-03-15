<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Accident</title>
</head>
<body>
<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Пользователь</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="name" items="${names}">
        <tr>
            <th scope="row"></th>
            <td><c:out value="${name}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>