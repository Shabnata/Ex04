<%@page import="DB.CookieDB"%>
<%@page import="Model.*"%>
<%
	//User currentUser = (User)request.getSession().getAttribute("user");
	Cookie currentUser = CookieDB.getCookieValue(request.getCookies(), "username");
	
%>



<%  if (currentUser != null) {%>
<h3>Logged in as: <%=currentUser.getValue()%></h3>
<form class="formStyle" action="LogoutServlet" method="post">
	<input type="submit" value="Log out"/>
</form>
<%}%>