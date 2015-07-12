<%@ page isErrorPage="true" import="java.io.PrintWriter" contentType="text/plain"%><%@ page import="java.io.StringWriter"%>
<html>
<body>
<%
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
%>
Some error happens....
<br />
Message: ${exception.message}
<br />
StackTrace:
<%
	StringWriter stringWriter = new StringWriter();
	PrintWriter printWriter = new PrintWriter(stringWriter);
	exception.printStackTrace(printWriter);
	out.println(stringWriter);
	printWriter.close();
	stringWriter.close();
%>
</body>
</html>