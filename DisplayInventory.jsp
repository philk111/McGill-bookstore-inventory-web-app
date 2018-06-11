<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import = "java.io.IOException, java.io.PrintWriter, javax.servlet.ServletException,
		javax.servlet.annotation.WebServlet, javax.servlet.http.HttpServlet, 
		javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
		org.mcgill.ccs2_505.assignment02.inventory.Inventory, org.mcgill.ccs2_505.assignment02.inventory.InventoryEntry" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>McGill Book Store Inventory System</title>
	</head>
	<body>
		<h1>McGill Bookstore Inventory Status</h1>
		<%! String msg = ""; %>
		<%
		Inventory inventory = (Inventory) getServletContext().getAttribute("inventory");
		response.setContentType("text/html");
		msg = (String) request.getAttribute("errorMsg");
		if(msg != null) {
			out.print("<font color=\"red\"><strong>" + msg + "</strong></font>");
		}
		
		/* Print out the Books */
		out.println("<h2>Books</h2><table border=\"1\">"
				+ "<tr><th>UID</th><th>QTY</th><th>Price</th><th>Author</th></tr>");
		for(String id : inventory.getProductIDs()) {
			InventoryEntry ie = inventory.get(id);
			if(ie.getProductType().equals("BOOK")) {
				%>
				<tr>
					<td><%= id %></td>
					<td><%= ie.getQuantity() %></td>
					<td><%= ie.getPrice() %></td>
					<td><%= ie.getAuthor() %></td>
				</tr>
				<%
			}
		}
		out.println("</table>");
		
		/* Print out the CompactDiscs */
		out.println("<h2>CompactDiscs</h2><table border=\"1\">"
				+ "<tr><th>UID</th><th>QTY</th><th>Price</th><th>Artist</th></tr>");
		%>
		<c:forEach var="id" items="${ inventory.getProductIDs() }">
			<c:if test="${ inventory.get(id).getProductType() eq 'COMPACTDISC' }">
				<tr>
					<td> ${ inventory.get(id).getID() }</td>
					<td> ${ inventory.get(id).getQuantity() }</td>
					<td> ${ inventory.get(id).getPrice() }</td>
					<td> ${ inventory.get(id).getArtist() }</td>
				</tr>
			</c:if>
		</c:forEach>
		<%
		out.println("</table>");
		out.println("<p>Back to the</p><a href=\"" + response.encodeURL("form.html") + "\">Form</a>");
		out.println("<a href=\"" + response.encodeURL("logout.jsp") + "\">Logout</a>");
		%>		
	</body>
</html>