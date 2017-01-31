<%@page import="DB.CookieDB"%>
<%@page import="Model.*"%>
<%
	//User currentUser = (User)request.getSession().getAttribute("user");
	Cookie currentUser = CookieDB.getCookieValue(request.getCookies(), "username");
%>


<!-- Need to change the logic-->
<%  if (currentUser == null) {%>
<jsp:include page="MenuGuest.jsp" />
<%  } else if (currentUser.getValue().equals("admin")) {%>
<jsp:include page="MenuAdmin.jsp" />
<%  } else {%>
<jsp:include page="MenuUser.jsp" />
<%}%>
