
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
		<%-- TODO Make title dynamic. --%>
		<title>AddStudentPage</title>
		<link href="Library.css" rel="stylesheet" type="text/css"/>
	</head>
    <body>
		<jsp:useBean id="student" class="Model.Student" scope="request" />
		<div id="outerContainer">
			<jsp:include page="Header.jsp" />
			<div id="centerBox">
				<div id="leftMenu">
					<jsp:include page="MenuGuest.jsp" />
					<br/><br/><br/>
					<jsp:include page="LoginForm.jsp" />
				</div> <%-- id=leftMenu --%>
				<div id="contentArea">
					<jsp:include page="AddStudentForm.jsp" />

					Student information:

					<table>
						<tr>
							<th>ID</th>
							<th>First name</th>
							<th>Last name</th>
							<th>Email</th>
							<th>Fines</th>
							<th>Pay fines</th>
						</tr>
						<tr>
							<td><%=student.getStudentID()%></td>
							<td><%=student.getFirstName()%></td>
							<td><%=student.getLastName()%></td>
							<td><%=student.getEmailAddress()%></td>
							<td><%=student.getCurrentFines()%></td>
							<td>//TODO</td>
						</tr>


					</table>



				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<jsp:include page="Footer.jsp" />
		</div> <%-- id=outerContainer --%>
	</body>
</html>
