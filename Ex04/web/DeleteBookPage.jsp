<%--
    Document   : DeleteBookPage
    Created on : Feb 1, 2017, 9:20:32 PM
    Author     : Denis Sh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>DeleteBookPage</title>
		<link href="Library.css" rel="stylesheet" type="text/css"/>
		<script src="InputJavaScriptFile.js" type="text/javascript"></script>
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
					<%@include file="DeleteBookForm.jsp" %>
					<br/>
					<%
						String servletMessage = (String)request.getAttribute("servletMessage");
						if(servletMessage != null){%>
					<h3><%= servletMessage%></h3>
					<%}%>
				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<%@include file="Footer.jsp" %>
		</div> <%-- id=outerContainer --%>
	</body>
</html>
