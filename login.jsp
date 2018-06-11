<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>McGill Bookstore Login</title>
	</head>
	<body>
		<p>Login into the McGill Bookstore Inventory</p>
		<form method="POST" action="j_security_check">
			<input type="text" name="j_username" />
			<input type="password" name="j_password" />
			<input type="submit" value="Login">
		</form>
	</body>
</html>