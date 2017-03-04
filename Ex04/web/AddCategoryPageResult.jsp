
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Controller.*" %>
<%@page import="Model.*" %>
<%@page import="DB.*" %>
<!DOCTYPE html>
<html>
    <head>
		<%-- TODO Make title dynamic. --%>
		<title>AddCategory</title>
		<link href="Library.css" rel="stylesheet" type="text/css"/>
	</head>
    <body>

		<div id="outerContainer">
			<jsp:include page="Header.jsp" />
			<div id="centerBox">
				<div id="leftMenu">
					<jsp:include page="Menu.jsp" />
					<br/><br/><br/>
					<jsp:include page="LoginForm.jsp" />
				</div> <%-- id=leftMenu --%>
				<div id="contentArea">
					<jsp:include page="AddCategoryForm.jsp" />
					<h1>Category added successfully</h1>
					Existing categories:
					<% ArrayList<Category> cat = new ArrayList<Category>();
						CategoryDB cDb = new CategoryDB();
						cat = cDb.getCategories(); %>
					<ul>
						<%for (Category c : cat) {%>
						<li><%=c.getCatName()%></li>
							<%}%>
					</ul>
				</div> <%-- id=centerBox --%>
				<jsp:include page="Footer.jsp" />
			</div> <%-- id=outerContainer --%>
	</body>
	<%if (cDb != null) {
			cDb.closeConnection();
		}%>
</html>
