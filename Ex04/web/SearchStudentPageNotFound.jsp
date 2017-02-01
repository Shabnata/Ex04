
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
		<%-- TODO Make title dynamic. --%>
		<title>SearchStudentPage</title>
		<link href="Library.css" rel="stylesheet" type="text/css"/>
	</head>
    <body>
		<div id="outerContainer">
			<jsp:include page="Header.jsp" />
			<div id="centerBox">
				<div id="leftMenu">
					<jsp:include page="Menu.jsp" />
					<br/><br/><br/>
					
				</div> <%-- id=leftMenu --%>
				<div id="contentArea">
					<jsp:include page="SearchStudentForm.jsp" />
					<h1>Student not found</h1>
				</div> <%-- id=contentArea --%>
				
			</div> <%-- id=centerBox --%>
			<jsp:include page="Footer.jsp" />
		</div> <%-- id=outerContainer --%>
	</body>
</html>
