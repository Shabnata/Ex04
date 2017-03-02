<form class="formStyle" action="AddStudentServlet" method="POST">
	<span class="formTxt">Student ID:<span style="color: red;">*</span></span>
	<br/>
	<input type="number" name="id" min="0" max="999999999" placeholder="9 digits ID" onfocus="focusClearIDTextBox(this)" onblur="studentIDFill(this)" required/>
	<br/>
	<span class="formTxt">First name:<span style="color: red;">*</span></span>
	<br/>
	<input type="text" name="fname" placeholder="First name" pattern="[A-Za-z]{3,}" title="First name must be at least 3 letters long and not contain symbols or spaces or numbers." required/>
	<br/>
	<span class="formTxt">Last name:<span style="color: red;">*</span></span>
	<br/>
	<input type="text" name="lname" placeholder="Last name" pattern="[A-Za-z]{3,}" title="Last name must be at least 3 letters long and not contain symbols or spaces or numbers." required/>
	<br/>
	<span class="formTxt">Email:<span style="color: red;">*</span></span>
	<br/>
	<input type="email" name="email" required/>
	<br/>
	<input type="submit" value="Add"/>
	<br/>
</form>