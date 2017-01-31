
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
		<title>SearchBookPage</title>
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
					<jsp:include page="LogoutForm.jsp" />
				</div> <%-- id=leftMenu --%>
				<div id="contentArea">
					<jsp:include page="SearchBookForm.jsp" />
				</div> <%-- id=contentArea --%>
			</div> <%-- id=centerBox --%>
			<jsp:include page="Footer.jsp" />
		</div> <%-- id=outerContainer --%>
	</body>
</html>