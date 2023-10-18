<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Accident</title>
</head>
<body>
<div>
    Login as : ${user.username}
</div>
<a href="<c:url value='/create'/>">Добавить инцидент</a>
<table class="table">
    <thead>
    <tr>
        <th scope="col">Edit</th>
        <th scope="col">id</th>
        <th scope="col">Name</th>
        <th scope="col">Type</th>
        <th scope="col">Text</th>
        <th scope="col">Address</th>
        <th scope="col">Rules</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${accidents}" var="a">
        <tr>
            <td>
                <a href="<c:url value='/edit?id=${a.id}'/>">
                    <i class="fa fa-edit mr-3"></i>
                </a>
            </td>
            <td>
                <c:out value="${a.id}"/>
            </td>
            <td>
                <c:out value="${a.name}"/>
            </td>
            <td>
                <c:out value="${a.type.name}"/>
            </td>
            <td>
                <c:out value="${a.text}"/>
            </td>
            <td>
                <c:out value="${a.address}"/>
            </td>
            <td>
                <c:forEach items="${a.rules}" var="rule">
                    <option value="${rule.id}">${rule.name}</option>
                </c:forEach>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>