<%@page import="DB.ConditionDB"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="DB.BookDB"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.*"%>
<%@page import="DB.StudentDB"%>
<%@page import="DB.LoanDB"%>
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
					<%
						StudentDB stDB = new StudentDB();
					%>



					<!--								Real table	--------->
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
							<td><%=dateStr%></td>
							<td>DOTO - Fines per day* days late</td>
							<td>prev Copy condition<br/>
								<%=bc.getCopyCondition().getConDesc()%><br/>
								Choose new condition:
								<ul>
								<%for (Condition c : cnd) {%>
							<li>	<%=c.getConDesc()%> </li>
							<%}%>
							</ul>
							</td>
							<td>Fines</td>
							<td>Return book</td>
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
