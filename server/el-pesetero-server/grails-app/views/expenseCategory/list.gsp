
<%@ page import="es.elpesetero.ExpenseCategory" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'expenseCategory.label', default: 'ExpenseCategory')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-expenseCategory" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li>User: ${theUser?.mail}</li>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li class="exit"><g:link class="exit" controller="logout">Logout</g:link></li>
			</ul>
		</div>
		<div id="list-expenseCategory" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="expenseCategory.parent.label" default="Parent" /></th>
					
						<th><g:message code="expenseCategory.user.label" default="User" /></th>
					
						<g:sortableColumn property="name" title="${message(code: 'expenseCategory.name.label', default: 'Name')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${expenseCategoryInstanceList}" status="i" var="expenseCategoryInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${expenseCategoryInstance.id}">${fieldValue(bean: expenseCategoryInstance, field: "parent")}</g:link></td>
					
						<td>${fieldValue(bean: expenseCategoryInstance, field: "user.mail")}</td>
					
						<td>${fieldValue(bean: expenseCategoryInstance, field: "name")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${expenseCategoryInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
