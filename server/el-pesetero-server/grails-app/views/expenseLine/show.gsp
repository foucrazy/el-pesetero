
<%@ page import="es.elpesetero.ExpenseLine" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'expenseLine.label', default: 'ExpenseLine')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-expenseLine" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li class="exit"><g:link class="exit" controller="logout">Logout</g:link></li>
			</ul>
		</div>
		<div id="show-expenseLine" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list expenseLine">
			
				<g:if test="${expenseLineInstance?.expense}">
				<li class="fieldcontain">
					<span id="expense-label" class="property-label"><g:message code="expenseLine.expense.label" default="Expense" /></span>
					
						<span class="property-value" aria-labelledby="expense-label"><g:link controller="expense" action="show" id="${expenseLineInstance?.expense?.id}">${expenseLineInstance?.expense?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${expenseLineInstance?.expenseDate}">
				<li class="fieldcontain">
					<span id="expenseDate-label" class="property-label"><g:message code="expenseLine.expenseDate.label" default="Expense Date" /></span>
					
						<span class="property-value" aria-labelledby="expenseDate-label"><g:formatDate date="${expenseLineInstance?.expenseDate}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${expenseLineInstance?.id}" />
					<g:link class="edit" action="edit" id="${expenseLineInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
