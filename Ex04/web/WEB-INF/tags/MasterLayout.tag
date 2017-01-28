
<%@tag description="Master layout" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@attribute name="menu" type="java.lang.String" required="true" %>
<%@attribute name="content" fragment="true" required="true" %>
<%@attribute name="pageTitle" required="true"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${pageTitle}</title>
		<link href="Library.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<div id="outerContainer">
			<t:Header/>
			<div id="centerBox">
				<div id="leftMenu">
					<% if(menu.equals("admin")){ %>
					<t:MenuAdmin/>
					<% } else if(menu.equals("user")){ %>
					<t:MenuUser/>
					<% } else { %>
					<t:MenuGuest/>
					<% }%>
				</div> <%-- id=leftMenu --%>
				<div id="contentArea">
					<jsp:invoke fragment="content"/>
				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<t:Footer/>
		</div> <%-- id=outerContainer --%>
	</body>
</html>