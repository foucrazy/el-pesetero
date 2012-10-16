
<%@ page import="es.elpesetero.security.SecurityUser" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'securityUser.label', default: 'SecurityUser')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-securityUser" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-securityUser" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="username" title="${message(code: 'securityUser.username.label', default: 'Username')}" />
					
						<g:sortableColumn property="password" title="${message(code: 'securityUser.password.label', default: 'Password')}" />
					
						<g:sortableColumn property="accountExpired" title="${message(code: 'securityUser.accountExpired.label', default: 'Account Expired')}" />
					
						<g:sortableColumn property="accountLocked" title="${message(code: 'securityUser.accountLocked.label', default: 'Account Locked')}" />
					
						<g:sortableColumn property="enabled" title="${message(code: 'securityUser.enabled.label', default: 'Enabled')}" />
					
						<g:sortableColumn property="passwordExpired" title="${message(code: 'securityUser.passwordExpired.label', default: 'Password Expired')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${securityUserInstanceList}" status="i" var="securityUserInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${securityUserInstance.id}">${fieldValue(bean: securityUserInstance, field: "username")}</g:link></td>
					
						<td>${fieldValue(bean: securityUserInstance, field: "password")}</td>
					
						<td><g:formatBoolean boolean="${securityUserInstance.accountExpired}" /></td>
					
						<td><g:formatBoolean boolean="${securityUserInstance.accountLocked}" /></td>
					
						<td><g:formatBoolean boolean="${securityUserInstance.enabled}" /></td>
					
						<td><g:formatBoolean boolean="${securityUserInstance.passwordExpired}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${securityUserInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
