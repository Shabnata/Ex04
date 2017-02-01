<%--
    Document   : AddBookPage
    Created on : Jan 31, 2017, 3:30:15 PM
    Author     : Denis Sh
--%>

<%@page import="Model.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>AddBookPage</title>
		<link href="Library.css" rel="stylesheet" type="text/css"/>
	</head>
    <body>
		<div id="outerContainer">
			<%@include file="Header.jsp" %>
			<div id="centerBox">
				<div id="leftMenu">
					<%@include file="Menu.jsp" %>
					<br/><br/><br/>
					<%@include file="LogoutForm.jsp" %>
				</div> <%-- id=leftMenu --%>
				<div id="contentArea">
					<%@include file="AddBookForm.jsp" %>
				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<%@include file="Footer.jsp" %>
		</div> <%-- id=outerContainer --%>
	</body>
</html>



