<html>
<head>
    <title>Login</title>
    <%
        String ctx = request.getContextPath();
        request.setAttribute("ctx", ctx);
    %>
</head>
<body>
<form action="${ctx}/admin/login" method="post" enctype="multipart/form-data" id="login_form">
    <input type="text" name="userName" id="user_name"/>
    <input type="password" name="password" id="password"/>
    <input type="submit" value="login" id="submit" class="btn btn-warning btn-sm"/>
</form>
</body>
</html>
