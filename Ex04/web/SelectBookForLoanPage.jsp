<%--
    Document   : SelectBookForLoanPage
    Created on : Feb 6, 2017, 11:30:51 AM
    Author     : Denis Sh
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Model.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
		<title>SelectBookForLoanPage</title>
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
						String stId = (String)request.getAttribute("stId");
						String loanId = (String)request.getAttribute("loanId");
					%>
					<%--
					<jsp:useBean id="stId" type="String" scope="request"/>
					<jsp:useBean id="loanId" type="String" scope="request"/>
					--%>
					<jsp:useBean id="bookLst" type="ArrayList<Book>" scope="request"/>

					<form action="AddLoanServlet" method="post">
						<input type="submit" value="Return"/>
						<%if(stId != null){%>
						<input type="hidden" name="stId" value="${stId}"/>
						<%}%>
						<%if(loanId != null){%>
						<input type="hidden" name="loanId" value="${loanId}"/>
						<%}%>
					</form><br/>
					<table id="bookSearchRes">
						<tr>
							<th>Cover</th>
							<th>Isbn</th>
							<th>Title</th>
							<th>Author</th>
							<th>Category</th>
							<th>Publication year</th>
							<th>Number of copies</th>
							<th>Loan Book</th>
						</tr>

						<% for(Book bk : bookLst){%>
						<tr>
							<td><img src=<%= bk.getCoverPath()%> alt=<%= bk.getTitle()%> style="height: 80px;"/></td>
							<td><%= bk.getISBN()%></td>
							<td><%= bk.getTitle()%></td>
							<td><%= bk.getAuthorName()%></td>
							<td><%= bk.getCategory().getCatName()%></td>
							<td><%= bk.getBookYear()%></td>
							<td><%= bk.getAvailableCopies()%></td>
							<% if(bk.getAvailableCopies() != 0){%>
							<td>
								<form action="AddLoanServlet" method="post">
									<input type="submit" value="Loan"/>
									<%if(stId != null){%>
									<input type="hidden" name="stId" value="${stId}"/>
									<%}%>
									<%if(loanId != null){%>
									<input type="hidden" name="loanId" value="${loanId}"/>
									<%}%>
									<input type="hidden" value="<%= bk.getISBN()%>" name="bookIsbn"/>

								</form>
							</td>
							<% } else { %>
							<td>
								-
							</td>
							<% } %>
						</tr>
						<% }%>
					</table>

				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<%@include file="Footer.jsp" %>
		</div> <%-- id=outerContainer --%>
	</body>
</html>