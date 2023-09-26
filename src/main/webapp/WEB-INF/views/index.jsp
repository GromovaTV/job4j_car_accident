<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <title>Accident</title>
</head>
<body>
<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">ФИО</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="u" varStatus="status">
        <tr>
            <td>
                <c:out value="${status.index + 1}"/>
            </td>
            <td>
                <c:out value="${u}"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>