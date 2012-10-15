
<%@ page import="es.elpesetero.Fund" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'fund.label', default: 'Fund')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-fund" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li class="exit"><g:link class="exit" controller="logout">Logout</g:link></li>
			</ul>
		</div>
		<div id="list-fund" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'fund.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="quantity" title="${message(code: 'fund.quantity.label', default: 'Quantity')}" />
					
						<g:sortableColumn property="type" title="${message(code: 'fund.type.label', default: 'Type')}" />
					
						<th><g:message code="fund.user.label" default="User" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${fundInstanceList}" status="i" var="fundInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${fundInstance.id}">${fieldValue(bean: fundInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: fundInstance, field: "quantity")}</td>
					
						<td>${fieldValue(bean: fundInstance, field: "type")}</td>
					
						<td>${fieldValue(bean: fundInstance, field: "user")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${fundInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
