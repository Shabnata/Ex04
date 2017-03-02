<%--
   Document   : SelectCategoryForLoan
   Created on : Feb 6, 2017, 11:09:08 AM
   Author     : Denis Sh
--%>

<%@page import="java.time.Year"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="Model.Category"%>
<%@page import="Model.Loan"%>
<%@page import="Model.Book"%>
<%@page import="Model.BookCopy"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
		<title>SelectCategoryForLoanPage</title>
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
					<%
						String stId = (String) request.getAttribute("stId");
						String loanId = (String) request.getAttribute("loanId");
					%>
					<jsp:useBean id="catLst" type="ArrayList<Model.Category>" scope="request"/>

					<form action="AddLoanServlet" method="post">
						<input type="submit" value="Return"/>
						<%if (stId != null) {%>
						<input type="hidden" name="stId" value="${stId}"/>
						<%}%>
						<%if (loanId != null) {%>
						<input type="hidden" name="loanId" value="<%= loanId%>"/>
						<%}%>
					</form><br/>
					<form class="formStyle" action="AddLoanServlet" method="post">

						<%if (stId != null) {%>
						<input type="hidden" name="stId" value="${stId}"/>
						<%}%>
						<%if (loanId != null) {%>
						<input type="hidden" name="loanId" value="<%= loanId%>"/>
						<%}%>

						<%
							boolean flagFirst = true;
							for (Category c : catLst) {
								if (flagFirst) {
						%>
						<input type="radio" name="catName" checked="checked" value="<%= c.getCatName()%>"/> <%= c.getCatName()%> <br/>
						<%
							flagFirst = false;
						} else {
						%>
						<input type="radio" name="catName" value="<%= c.getCatName()%>"/> <%= c.getCatName()%> <br/>
						<%
								}
							}
						%>
						<input type="submit" value="Select"/>
					</form>
					<br/><br/><br/>

					<%
						Loan ln = (Loan) request.getAttribute("loan");
						HashMap<String, Book> bksLst = (HashMap<String, Book>) request.getAttribute("bksMap");
						if (ln != null) {
					%>
					<hr/>
					<br/>
					Books in current loan:
					<table id="booksInLoan">
						<tr>
							<th>Cover</th>
							<th>BookCopy number</th>
							<th>Title</th>
							<th>Author</th>
							<th>Category</th>
							<th>Publication year</th>
						</tr>

						<%
							for (BookCopy bc : ln.getBooksInLoan()) {
								Book bk = bksLst.get(bc.getCOPY_CODE());
								String isbnCopy = bc.getCOPY_CODE();
								String bookTitle = bk.getTitle();
								String author = bk.getAuthorName();
								String bookCategory = bk.getCategory().getCatName();
								Year publication = bk.getBookYear();
						%>

						<tr>
							<td><img src="<%= bk.getCoverPath()%>" alt="<%= bookTitle%>" style="height: 80px;"/></td>
							<td><%= isbnCopy%></td>
							<td><%= bookTitle%></td>
							<td><%= author%></td>
							<td><%= bookCategory%></td>
							<td><%= publication%></td>
						</tr>
						<%
							}
						%>
					</table>
					<%
						}
					%>
				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<%@include file="Footer.jsp" %>
		</div> <%-- id=outerContainer --%>
	</body>
</html>