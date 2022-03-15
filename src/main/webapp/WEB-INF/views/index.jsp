<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


    <title>Accident</title>
</head>
<body>
<h3 style="text-align: center" >Accidents</h3>
<p class="lead" style="text-align: center">
<div class="container" width="70%">
<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Название</th>
        <th scope="col">Описание</th>
        <th scope="col">Адрес</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="accident" items="${accidents}">
        <tr>
            <th scope="row"></th>
            <td><c:out value="${accident.name}"/></td>
            <td><c:out value="${accident.text}"/></td>
            <td><c:out value="${accident.address}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>
</body>
</html>