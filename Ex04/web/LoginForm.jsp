<%@page import="DB.CookieDB"%>
<%@page import="Model.*"%>
<%
	//User currentUser = (User)request.getSession().getAttribute("user");
	Cookie currentUser = CookieDB.getCookieValue(request.getCookies(), "username");
%>


<!-- Need to change the logic-->
<%  if (currentUser == null) {%>
<h3>Login:</h3>
<form class="formStyle" action="LoginServlet" method="post">
	<span class="formTxt">Username:</span></br>
	<input type="text" name="Username" required/></br>
	<span class="formTxt">Password:</span></br>
	<input type="password" name="Password" required/></br>
	<input type="submit" value="Log In"/>
</form></br>
Use login: admin</br>
password: admin

<%}%>






