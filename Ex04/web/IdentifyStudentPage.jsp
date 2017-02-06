<%--
    Document   : IdentifyStudentPage
    Created on : Feb 6, 2017, 10:05:29 AM
    Author     : Denis Sh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
		<title>IdentifyStudentPage</title>
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
					<jsp:useBean id="bookISBN" type="java.lang.String" scope="request"/>
					<jsp:useBean id="stNotFound" type="java.lang.String" scope="request"/>
					<form class="formStyle" action="LoanBookFromSearchServlet" method="post">
						<span class="formTxt">Find student by ID:</span><br/>
						<input type="number" name="stId" min="0" max="999999999" placeholder="9 digits ID" onfocus="focusClearIDTextBox(this)" onblur="studentIDFill(this)" required/>
						<br/>
						<input type="hidden" name="bookIsbn" value="${bookISBN}"/>
						<input type="submit" value="Search"
					</form>
					<h1>${stNotFound}</h1>
				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<%@include file="Footer.jsp" %>
		</div> <%-- id=outerContainer --%>
	</body>
</html>