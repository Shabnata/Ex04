
<%@tag description="Master layout" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@attribute name="menu" type="java.lang.String" required="true" %>
<%@attribute name="content" fragment="true" %>
<%@attribute name="pageTitle" required="true" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${pageTitle}</title>
		<link href="Library.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<div id="outerContainer">
			<jsp:include page="/WEB-INF/jspf/HeaderFragment.jspf" />
			<div id="centerBox">
				<div id="leftMenu">
					<% if(menu.equals("admin")){ %>
					<jsp:include page="/WEB-INF/jspf/MenuAdminFragment.jspf" />
					<% } else if(menu.equals("user")){ %>
					<jsp:include page="/WEB-INF/jspf/MenuUserFragment.jspf" />
					<% } else { %>
					<jsp:include page="/WEB-INF/jspf/MenuGuestFragment.jspf" />
					<% }%>
					<br/><br/><br/>
				</div> <%-- id=leftMenu --%>
				<div id="contentArea">
					<jsp:doBody/>
				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<jsp:include page="/WEB-INF/jspf/FooterFragment.jspf" />
		</div> <%-- id=outerContainer --%>
	</body>
</html>