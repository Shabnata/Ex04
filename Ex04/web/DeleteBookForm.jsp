<%--
    Document   : DeleteBookForm
    Created on : Feb 1, 2017, 9:21:00 PM
    Author     : Denis Sh
--%>

<form class="formStyle" action="DeleteBookServlet" method="POST">
	<div class="leftFormPart">
		<span class="formTxt">Delete book by ISBN:</span>
		<br/>
		<input type="number" name="bookIsbn" min="0" max="9999999999999" placeholder="13 digits ISBN" onfocus="focusClearISBNTextBox(this)" onblur="bookISBNFill(this)" required/>
		<br/>
		<input type="submit" value="Delete"/>
	</div>
	<br/>
</form>