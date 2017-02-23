<%--
   Document   : AddLoanPageError
   Created on : Feb 6, 2017, 3:40:00 PM
   Author     : Denis Sh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
		<title>AddLoanPageError</title>
		<link href="Library.css" rel="stylesheet" type="text/css"/>
	</head>
    <body>
		<div id="outerContainer">
			<%@include file="Header.jsp" %>
			<div id="centerBox">
				<div id="leftMenu">
					<%@include file="Menu.jsp" %>
					<br/><br/><br/>
				</div> <%-- id=leftMenu --%>
				<div id="contentArea">
					<jsp:useBean id="errors" type="java.util.ArrayList<java.lang.String>" scope="request"/>
					<% for (String err : errors) {%>
					<h3><%= err%></h3>
					<%}%>
				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<%@include file="Footer.jsp" %>
		</div> <%-- id=outerContainer --%>
	</body>
</html>