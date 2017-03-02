
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
		<%-- TODO Make title dynamic. --%>
		<title>SearchStudentPage</title>
		<link href="Library.css" rel="stylesheet" type="text/css"/>
	</head>
    <body>
		<div id="outerContainer">
			<jsp:include page="Header.jsp" />
			<div id="centerBox">
				<div id="leftMenu">
					<jsp:include page="Menu.jsp" />
					<br/><br/><br/>

				</div> <%-- id=leftMenu --%>
				<div id="contentArea">
					<form class="formStyle" action="ReturnBookServlet" method="POST">
						<div class="">
							<span class="formTxt">Find student by ID:</span><br/>
							<input type="number" name="stID" min="0" max="999999999" placeholder="9 digits ID" onfocus="focusClearIDTextBox(this)" onblur="studentIDFill(this)" required/>
							<br/>
							<input type="submit" value="Search"/>
						</div>
						<br/>
					</form>

					<%
						Boolean notFound = (request.getAttribute("StudentNotFound") != null && (Boolean)request.getAttribute("StudentNotFound") == true);
						if(notFound){
					%>
					<h1>Student not found</h1>
					<%}%>

				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<jsp:include page="Footer.jsp" />
		</div> <%-- id=outerContainer --%>
	</body>
</html>
