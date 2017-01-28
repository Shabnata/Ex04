
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:MasterLayout pageTitle="Search Student" menu="user">
	<jsp:attribute name="content">
		<form class="formStyle" action="SearchStudentServlet" method="post">
			<div class="leftFormPart">
				<span class="formTxt">Find student by ID:</span><br/>
				<input type="number" name="stID" min="0" max="999999999" placeholder="9 digits ID" required/>
				<br/>
				<input type="submit" value="Search"/>
			</div><br/>
		</form>
	</jsp:attribute>
</t:MasterLayout>