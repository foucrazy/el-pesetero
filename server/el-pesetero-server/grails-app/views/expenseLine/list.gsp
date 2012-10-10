
<%@ page import="es.elpesetero.ExpenseLine" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'expenseLine.label', default: 'ExpenseLine')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-expenseLine" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-expenseLine" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="expenseLine.expense.label" default="Expense" /></th>
					
						<g:sortableColumn property="expenseDate" title="${message(code: 'expenseLine.expenseDate.label', default: 'Expense Date')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${expenseLineInstanceList}" status="i" var="expenseLineInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${expenseLineInstance.id}">${fieldValue(bean: expenseLineInstance, field: "expense")}</g:link></td>
					
						<td><g:formatDate date="${expenseLineInstance.expenseDate}" /></td>
					
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
