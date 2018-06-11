<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page session="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>McGill Bookstore logout</title>
	</head>
	<body>
		<%session.invalidate(); %>
		<p>You have logged out!</p>
		
	</body>
	<script>
		window.location.href = "form.html";
	</script>
</html>