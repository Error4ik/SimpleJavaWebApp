<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Index Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</head>
<body>

<h2>Add or Update users.</h2>
<form id="add-update-form" action="${pageContext.servletContext.contextPath}/" method="post">
    <label for="id">Id:</label><br>
    <input type="text" id="id" name="id" placeholder="Use if update"><br/>
    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name" required="required"><br/>
    <label for="surname">Surname:</label><br>
    <input type="text" id="surname" name="surname" required="required"><br/>
    <label for="age">Age:</label><br>
    <input type="text" id="age" name="age" required="required"><br/>
    <label for="email">Email:</label><br/>
    <input type="text" id="email" name="email" required="required"><br/><br/>
    <input type="submit" name="update" value="Add or Update User">
</form>

<script>
    function deleteUser(id) {
        $.ajax({
            url: "./",
            method: "POST",
            data: {"type": "delete", "id": id},
            success: function () {
                console.log('Deleted!');
                window.location = './'
            }
        });
    }
</script>
<table border="3" style="width: 100%">
    <tr>
        <th style="width: 20%;">ID</th>
        <th style="width: 10%;">name</th>
        <th style="width: 10%;">surname</th>
        <th style="width: 10%;">age</th>
        <th style="width: 10%;">email</th>
        <th style="width: 10%;">date & time:</th>
        <th style="width: 5%;">Delete</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.id}"></c:out>
            </td>
            <td>
                <c:out value="${user.name}"></c:out>
            </td>
            <td>
                <c:out value="${user.surname}"></c:out>
            </td>
            <td>
                <c:out value="${user.age}"></c:out>
            </td>
            <td>
                <c:out value="${user.email}"></c:out>
            </td>
            <td>
                <strong><fmt:formatDate type="both" value="${user.date}" /></strong>
            </td>
            <td>
                <button type='button' id='deleteButton' onclick='deleteUser("${user.id}")'>Delete</button>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
