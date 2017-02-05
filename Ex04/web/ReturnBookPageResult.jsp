<%@page import="DB.*"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.*"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
		<%-- TODO Make title dynamic. --%>
		<title>ReturnBookPage</title>
		<link href="Library.css" rel="stylesheet" type="text/css"/>
	</head>
    <body>
		<%
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			String urlCn = "jdbc:derby://localhost:1527/LibraryDB";
			Connection cn = DriverManager.getConnection(urlCn, "administrator", "123456");

			LibraryPropsDB libDB = new LibraryPropsDB(cn);
			int finesPerDay = libDB.getFinesPerDay();
			StudentDB stDB = new StudentDB();

		%>

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


					<table>
						<tr>
							<th>LoanID</th>
							<th>Cover</th>
							<th>BookISBN</th> 
							<th>Title</th>
							<th>Copy code</th>
							<th>Return by date</th>
							<th>Fines for lateness</th>
							<th>Copy condition</th>
							<th>Fines</th>
							<th>Return book</th>
						</tr>
						<%
							ArrayList<Loan> loans = new ArrayList<Loan>();
							loans = stDB.getLoans(student.getStudentID());
							for (Loan ln : loans) {
								ArrayList<BookCopy> bcs = new ArrayList<BookCopy>();
								bcs = ln.getBooksInLoan();
								for (BookCopy bc : bcs) {
									BookDB bDB = new BookDB(cn);
									ConditionDB cndDB = new ConditionDB();
									ArrayList<Condition> cnd = new ArrayList<Condition>();
									cnd = cndDB.getConditions();
									Book b = new Book();
									b = bDB.getBookByBookCopy(bc);
									GregorianCalendar rtrnDate = ln.getReturnByDate();
									String dateStr = rtrnDate.get(GregorianCalendar.DAY_OF_MONTH) + "." + (rtrnDate.get(GregorianCalendar.MONTH) + 1) + "." + rtrnDate.get(GregorianCalendar.YEAR);


						%>
						<tr>
							<td><%=ln.getLoanID()%></td>
							<td><img src="<%=b.getCoverPath()%>" style="height: 80px;"></td>
							<td><%=b.getISBN()%></td> 
							<td><%=b.getTitle()%></td>
							<td><%=bc.getCOPY_CODE()%></td>



							<%LoanDB lnDB = new LoanDB(cn);
								if (lnDB.isOverdue(ln.getReturnByDate()) < 0) {

							%>

							<td><span style="color: red;"><%=dateStr%></span></td>


							<!--change from 1 to calc-->
							<td><%=lnDB.isOverdue(ln.getReturnByDate()) * finesPerDay * -1%></td>

							<%} else {%>
							<td><%=dateStr%></td>
							<td>0</td>
							<%}%>


						<form class=formStyle action=ReturnBookActionServlet method=POST>
							<td>Prev cond:<br/>
								<%=bc.getCopyCondition().getConDesc()%><br/>
								New cond:
								<select name=newCondition>
									<%for (Condition c : cnd) {%>
									<option value="<%=c.getConKey()%>"><%=c.getConDesc()%></option>
									<%}%>
								</select>
							</td>

							<td>
								<input type="number" name="generalFines" min="0">
							</td>

							<!--change from 1 to calc-->
							<%
								int lateFine = ((lnDB.isOverdue(ln.getReturnByDate()) * finesPerDay) < 0) ? (int) (lnDB.isOverdue(ln.getReturnByDate()) * finesPerDay * -1) : 0;
							%>



							<td>
								
								<input type=hidden name=loanID value="<%=ln.getLoanID()%>"/>
								<input type=hidden name=studentID value="<%=student.getStudentID()%>"/>
								<input type=hidden name=lateFines value="todo lateFine" />
								<input type=hidden name=copyCode value="<%=bc.getCOPY_CODE()%>" />
								<input type=submit value="Return copy"/>
							</td>
						</form>
						<!--							<td>Fines</td>
													<td>Return book</td>-->
						<%}%>


						</tr>
						<%	}%>





					</table>

				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<jsp:include page="Footer.jsp" />
		</div> <%-- id=outerContainer --%>
	</body>
</html>
