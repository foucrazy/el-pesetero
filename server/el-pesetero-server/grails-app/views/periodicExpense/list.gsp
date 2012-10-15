
<%@ page import="es.elpesetero.PeriodicExpense" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'periodicExpense.label', default: 'PeriodicExpense')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-periodicExpense" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li class="exit"><g:link class="exit" controller="logout">Logout</g:link></li>
			</ul>
		</div>
		<div id="list-periodicExpense" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="day" title="${message(code: 'periodicExpense.day.label', default: 'Day')}" />
					
						<th><g:message code="periodicExpense.expense.label" default="Expense" /></th>
					
						<g:sortableColumn property="frequency" title="${message(code: 'periodicExpense.frequency.label', default: 'Frequency')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${periodicExpenseInstanceList}" status="i" var="periodicExpenseInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${periodicExpenseInstance.id}">${fieldValue(bean: periodicExpenseInstance, field: "day")}</g:link></td>
					
						<td>${fieldValue(bean: periodicExpenseInstance, field: "expense")}</td>
					
						<td>${fieldValue(bean: periodicExpenseInstance, field: "frequency")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${periodicExpenseInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
