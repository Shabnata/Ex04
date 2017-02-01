<%--
    Document   : AddBookPageNotAdded
    Created on : Jan 31, 2017, 4:47:34 PM
    Author     : Denis Sh
--%>

<%@page import="Model.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>AddBookPageNotAdded</title>
		<link href="Library.css" rel="stylesheet" type="text/css"/>
	</head>
    <body>
		<div id="outerContainer">
			<%@include file="Header.jsp"%>
			<div id="centerBox">
				<div id="leftMenu">
					<%@include file="Menu.jsp" %>
					<br/><br/><br/>
				</div> <%-- id=leftMenu --%>
				<div id="contentArea">
					<%@include file="AddBookForm.jsp" %>
					<h1>Could not add book</h1>
				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<%@include file="Footer.jsp" %>
		</div> <%-- id=outerContainer --%>
	</body>
</html>
