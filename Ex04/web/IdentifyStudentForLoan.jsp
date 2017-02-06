<%--
    Document   : IdentifyStudentForLoan
    Created on : Feb 6, 2017, 11:07:18 AM
    Author     : Denis Sh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
		<title>IdentifyStudentForLoanPage</title>
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
					<form class="formStyle" action="AddLoanServlet" method="post">
						<span class="formTxt">Find student by ID:</span><br/>
						<input type="number" name="stId" min="0" max="999999999" placeholder="9 digits ID" onfocus="focusClearIDTextBox(this)" onblur="studentIDFill(this)" required/>
						<br/>
						<input type="submit" value="Search"
					</form>
				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<%@include file="Footer.jsp" %>
		</div> <%-- id=outerContainer --%>
	</body>
</html>