<%--
    Document   : SelectCategoryForLoan
    Created on : Feb 6, 2017, 11:09:08 AM
    Author     : Denis Sh
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Model.Category"%>
<!DOCTYPE html>
<html>
    <head>
		<title>IdentifyStudentPage</title>
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
					<jsp:useBean id="stId" type="String" scope="request"/>
					<jsp:useBean id="loanId" type="String" scope="request"/>
					<jsp:useBean id="catLst" type="ArrayList<Category>" scope="request"/>

					<form class="formStyle" action="AddLoanServlet" method="post">

						<%if(stId != null){%>
						<input type="hidden" name="stId" value="${stId}"/>
						<%}%>
						<%if(loanId != null){%>
						<input type="hidden" name="loanId" value="${loanId}"/>
						<%}%>

						<%
							boolean flagFirst = true;
							for(Category c : catLst){
								if(flagFirst){
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
				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<%@include file="Footer.jsp" %>
		</div> <%-- id=outerContainer --%>
	</body>
</html>