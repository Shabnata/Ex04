<%@page import="DB.CookieDB"%>
<%@page import="Model.*"%>
<%
	//User currentUser = (User)request.getSession().getAttribute("user");
	Cookie currentUser = CookieDB.getCookieValue(request.getCookies(), "username");
%>


<!-- Need to change the logic-->
<%  if (currentUser != null) {%>
<form class="formStyle" action="LogoutServlet" method="post">
	<input type="submit" value="Log out"/>
</form>
<%}%>