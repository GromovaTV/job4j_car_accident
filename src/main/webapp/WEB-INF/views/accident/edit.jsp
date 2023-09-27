<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<form  action="<c:url value='/save'/>" method='POST'>
    <table>
        <tr>
            <td><input type='hidden' name='id' value="<c:out value="${a.id}"/>"></td>
        </tr>
        <tr>
            <td>Название:</td>
            <td><input type='text' name='name' value="<c:out value="${a.name}"/>"></td>
        </tr>
        <tr>
            <td>Тип:</td>
            <td>
                <select name="type_id">
                    <c:forEach var="type" items="${types}" >
                        <c:choose>
                            <c:when test="${type.id eq a.type.id}">
                                <option value="${type.id}" selected>${type.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${type.id}">${type.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Статьи:</td>
            <td>
                <select name="rIds" multiple>
                    <c:forEach var="rule" items="${rules}" >
                        <option value="${rule.id}">${rule.name}</option>
                    </c:forEach>
                </select>
        </tr>
        <tr>
            <td>Причина:</td>
            <td><input type='text' name='text' value="<c:out value="${a.text}"/>"></td>
        </tr>
        <tr>
            <td>Адрес:</td>
            <td><input type='text' name='address' value="<c:out value="${a.address}"/>"></td>
        </tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit" value="Сохранить" /></td>
        </tr>
    </table>
</form>
</body>
</html>