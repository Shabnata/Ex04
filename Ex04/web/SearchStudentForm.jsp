<form class="formStyle" action="SearchStudentServlet" method="POST">
	<div class="">
		<span class="formTxt">Find student by ID:</span><br/> 
		<input type="number" name="stID" min="0" max="999999999" placeholder="9 digits ID" onfocus="focusClearIDTextBox(this)" onblur="studentIDFill(this)" required/>
		<br/>
		<input type="submit" value="Search"/>
	</div>
	<br/>
</form>