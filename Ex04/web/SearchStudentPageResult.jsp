
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
					<jsp:include page="MenuGuest.jsp" />
					<br/><br/><br/>
					<jsp:include page="LoginForm.jsp" />
				</div> <%-- id=leftMenu --%>
				<div id="contentArea">
					<jsp:include page="SearchStudentForm.jsp" />

					Student information:

					<table>
						<tr>
							<th>ID</th>
							<th>First name</th>
							<th>Last name</th>
							<th>Email</th>
							<th>Number of books currently loaned</th>
							<th>Fines</th>
							<th>Pay fines</th>
						</tr>


					
					</table>



				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<jsp:include page="Footer.jsp" />
		</div> <%-- id=outerContainer --%>
	</body>
</html>
