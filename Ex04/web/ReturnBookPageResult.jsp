<%@page import="DB.StudentDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
		<%-- TODO Make title dynamic. --%>
		<title>ReturnBookPage</title>
		<link href="Library.css" rel="stylesheet" type="text/css"/>
	</head>
    <body>
		<jsp:useBean id="student" class="Model.Student" scope="request" />
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
					
<!--					Change table to print books that are loaned by this student-->
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
						<tr>
							<td><%=student.getStudentID()%></td>
							<td><%=student.getFirstName()%></td>
							<td><%=student.getLastName()%></td>
							<td><%=student.getEmailAddress()%></td>
							
							<%
							//get loaned bookks num
							StudentDB stDB= new StudentDB();
							int num = stDB.getCountLoanedBooks(student.getStudentID());
							%>
							<td><%=num%></td>
							<td><%=student.getCurrentFines()%></td>
							<td>
								<form class="formStyle" action="PayFineServlet" method="POST">
									<input type="number" name="payAmount" min=0 max="<%=student.getCurrentFines()%>"/>
									<input type="hidden" name="stID" value="<%=student.getStudentID()%>"/>
									<input type="submit" value="pay" />
								</form>
							</td>
						</tr>


					</table>
				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<jsp:include page="Footer.jsp" />
		</div> <%-- id=outerContainer --%>
	</body>
</html>
