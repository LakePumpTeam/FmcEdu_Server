<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${request.contextPath}/admin/login" method="post" enctype="multipart/form-data" id="login_form">
    <input type="text" name="userName" id="user_name"/>
    <input type="password" name="password" id="password"/>
    <input type="submit" value="login" id="submit" class="btn btn-warning btn-sm"/>
</form>
</body>
</html>
