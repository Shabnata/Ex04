<%@page import="DB.UserDB"%>
<%@page import="DB.CookieDB"%>
<%@page import="Model.*"%>
<%
	//User currentUser = (User)request.getSession().getAttribute("user");
	Cookie currentUser = CookieDB.getCookieValue(request.getCookies(), "username");
%>
<%!UserDB myUserDb = null;%>

<%  if (currentUser == null) {%>
<jsp:include page="MenuGuest.jsp" />
<%  } else if (currentUser != null) {
	myUserDb = new UserDB();
	User crrUser = myUserDb.getUser(currentUser.getValue());
	if (crrUser.getUserType().equals("admin")) {%>
<jsp:include page="MenuAdmin.jsp" />
<%} else {%>

<jsp:include page="MenuUser.jsp" />
<%}
	}%>
<jsp:include page="LoginForm.jsp" />
<jsp:include page="LogoutForm.jsp" />
<%if (myUserDb != null) {
		myUserDb.closeConnection();
	}%>







