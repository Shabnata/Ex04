
<%@tag description="Admin menu" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<h4>
	Common options:
</h4>
<a href="ReturnBookPageServlet">
	Return books
</a>
<br/>
<h4>
	Search options:
</h4>
<a href="SearchBookPageServlet">
	Find book
</a>
<br/>
<h4>
	Add Options:
</h4>
<a href="AddLoanPageServlet">
	Add new loan
</a>
<br/>
<br/>
<br/>
<t:LogoutForm/>