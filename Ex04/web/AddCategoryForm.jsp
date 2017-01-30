<form class="formStyle" action="AddCategoryServlet" method="POST">
	<div>
		<span class="formTxt">New category name:</span>
		<br/>
		<input type="text" name="category" pattern="[A-Za-z]{3,}" title="Category name must be at least 3 letters long and not contain symbols or spaces or numbers." required/>
		<br/>
		<input type="submit" value="Add"/>
	</div>
	<br/>
</form>
<br/>