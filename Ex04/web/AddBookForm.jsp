<%--
    Document   : AddBookForm
    Created on : Jan 31, 2017, 3:33:46 PM
    Author     : Denis Sh
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Model.Category"%>
<%@page import="DB.CategoryDB"%>
<form class="formStyle" action="AddBookServlet" method="post">
	<div class="leftFormPart">
		<span class="formTxt">Isbn:<span style="color: red;">*</span></span><br/>
		<input type="number" name="isbn" min="0" max="9999999999999" placeholder="13 digits ISBN" onfocus="focusClearISBNTextBox(this)" onblur="bookISBNFill(this)"required/><br/>
		<span class="formTxt">Title:<span style="color: red;">*</span></span><br/>
		<input type="text" name="title" placeholder="Book title" pattern="([ ]+[^ ]+.*)|([^ ]+.*)" title="Book title must be at least 1 letter long." required/><br/>
		<span class="formTxt">Url to Cover:<span style="color: red;">*</span></span><br/>
		<input type="url" name="cover" placeholder="http://"required/><br/>
		<span class="formTxt">Author:<span style="color: red;">*</span></span><br/>
		<input type="text" name="author" placeholder="Author name" pattern="[A-Za-z ]{3,}" title="Author name must be at least 3 letters long." required/><br/>
		<span class="formTxt">Publication year:<span style="color: red;">*</span></span><br/>
		<input type="number" name="year" min="1900" max="2017" required/><br/>
		<input type="submit" value="Add"/>
	</div>
	<div class="rightFormPart">
		<span class="formTxt">Category:<span style="color: red;">*</span></span><br/>
		<jsp:useBean id="categories" class="java.util.ArrayList<Category>" type="java.util.ArrayList<Category>" scope="request"/>
		<%
			boolean flagFirst = true;
			for(Category c : categories){
				if(flagFirst){
		%>
		<input type="radio" name="category" checked="checked" value="<%= c.getCatName()%>"/> <%= c.getCatName()%> <br/>
		<%
			flagFirst = false;
		} else {
		%>
		<input type="radio" name="category" value="<%= c.getCatName()%>"/> <%= c.getCatName()%> <br/>
		<%
				}
			}
		%>
		<br>
		<span class="formTxt">Number of copies:<span style="color: red;">*</span></span>
		<input type="number" name="numCopies" min="0" required/>
	</div>

</form>