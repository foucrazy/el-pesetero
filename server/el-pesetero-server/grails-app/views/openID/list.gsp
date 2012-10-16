
<%@ page import="es.elpesetero.security.OpenID" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'openID.label', default: 'OpenID')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-openID" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-openID" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="url" title="${message(code: 'openID.url.label', default: 'Url')}" />
					
						<th><g:message code="openID.user.label" default="User" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${openIDInstanceList}" status="i" var="openIDInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${openIDInstance.id}">${fieldValue(bean: openIDInstance, field: "url")}</g:link></td>
					
						<td>${fieldValue(bean: openIDInstance, field: "user")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${openIDInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
