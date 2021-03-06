
<%@page import="Model.Book"%>
<%@page import="DB.UserDB"%>
<%@page import="DB.CookieDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
		<title>SearchBookPageResult</title>
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
					<%@include file="SearchBookForm.jsp" %><br/>
					<hr/><br/>
					<jsp:useBean id="bksLst" type="java.util.ArrayList<Model.Book>" scope="request"/>

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

						<% for(Book bk : bksLst){%>
						<tr>
							<td><img src=<%= bk.getCoverPath()%> alt=<%= bk.getTitle()%> style="height: 80px;"/></td>
							<td><%= bk.getISBN()%></td>
							<td><%= bk.getTitle()%></td>
							<td><%= bk.getAuthorName()%></td>
							<td><%= bk.getCategory().getCatName()%></td>
							<td><%= bk.getBookYear()%></td>
							<td><%= bk.getAvailableCopies()%></td>
							<% if(bk.getAvailableCopies() != 0 && currentUser != null){%>
							<td>
								<form action="AddLoanServlet" method="post">
									<input type="submit" value="Loan"/>
									<input type="hidden" value="<%= bk.getISBN()%>" name="bookIsbn"/>
									<%--
									Unable to use existing objects of myUserDb and crrUser,
									because they are defined in an "if" scope in Menu.jsp
									thus they only exist in that scope.
									--%>
									<%!
										UserDB myUserDb2 = null;
									%>
									<%
										myUserDb2 = new UserDB();
										User crrUser2 = myUserDb2.getUser(currentUser.getValue());
										if(crrUser2.getUserType().equals("user")){
									%>
									<input type="hidden" value="<%= crrUser2.getUserID()%>" name="stId"/>
									<%
										}
									%>
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
	<%if(myUserDb2 != null){
			myUserDb2.closeConnection();
		}%>
</html>
