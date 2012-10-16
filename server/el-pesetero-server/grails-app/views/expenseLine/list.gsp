
<%@ page import="es.elpesetero.ExpenseLine" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'expenseLine.label', default: 'ExpenseLine')}" />
		<g:if test="${category}">
			<title>Gastos de la categoría ${category.name}</title>
		</g:if>
		<g:if test="${!category}">
			<title><g:message code="default.list.label" args="[entityName]" /></title>
		</g:if>
	</head>
	<body>
		<a href="#list-expenseLine" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li class="exit"><g:link class="exit" controller="logout">Logout</g:link></li>
			</ul>
		</div>
		<div id="list-expenseLine" class="content scaffold-list" role="main">
			<g:if test="${category}">
				<h1><g:message code="expenseLine.category.list.label" args="[category]" /></h1>
			</g:if>
			<g:if test="${!category}">
				<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			</g:if>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="expenseDate" title="${message(code: 'expenseLine.expenseDate.label', default: 'Expense Date')}" />

						<th><g:message code="expenseLine.expense.label" default="Expense" /></th>
									
						<g:sortableColumn property="expense.quantity" title="${message(code: 'expense.quantity.label', default: 'Quantity')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${expenseLineInstanceList}" status="i" var="expenseLineInstance">
					<g:set var="expenseInstance" value="${expenseLineInstance.expense}"/>
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:formatDate date="${expenseLineInstance.expenseDate}" /></td>

						<td><g:link action="show" id="${expenseLineInstance.id}">${fieldValue(bean: expenseLineInstance, field: "expense.name")}</g:link></td>					
					
						<td>${fieldValue(bean: expenseInstance, field: "quantity")}</td>	
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${expenseLineInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
