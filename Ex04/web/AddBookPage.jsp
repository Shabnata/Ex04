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
			<jsp:include page="Header.jsp"/>
			<div id="centerBox">
				<div id="leftMenu">
					<jsp:include page="Menu.jsp"/>
					<br/><br/><br/>
					
				</div> <%-- id=leftMenu --%>
				<div id="contentArea">
					<jsp:include page="AddBookForm.jsp"/>
				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<jsp:include page="Footer.jsp" />
		</div> <%-- id=outerContainer --%>
	</body>
</html>



