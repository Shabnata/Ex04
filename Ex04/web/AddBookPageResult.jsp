<%--
   Document   : AddBookPageResult
   Created on : Jan 31, 2017, 4:47:46 PM
   Author     : Denis Sh
--%>

<%@page import="Model.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>AddBookPageResult</title>
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
					<%@include file="AddBookForm.jsp" %>
					<br/><br/><hr/><br/>
					<jsp:useBean id="book" type="Model.Book" scope="request"/>

					<table id="bookSearchRes">
						<tr>
							<th>Cover</th>
							<th>Isbn</th>
							<th>Title</th>
							<th>Author</th>
							<th>Category</th>
							<th>Publication year</th>
							<th>Number of copies</th>
						</tr>
						<tr>
							<td><img src=${book.coverPath} alt=${book.title} style="height: 80px;"/></td>
							<td>${book.ISBN}</td>
							<td>${book.title}</td>
							<td>${book.authorName}</td>
							<td>${book.category.catName}</td>
							<td>${book.bookYear}</td>
							<td>${book.availableCopies}</td>
						</tr>
					</table>

				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<%@include file="Footer.jsp" %>
		</div> <%-- id=outerContainer --%>
	</body>
</html>
