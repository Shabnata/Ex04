
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="Header.jsp" />
<jsp:include page="MenuGuest.jsp" />
<jsp:include page="SearchBookForm.jsp" />
<hr/>
<br/>
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
	</table>
<jsp:include page="Footer.jsp" />
