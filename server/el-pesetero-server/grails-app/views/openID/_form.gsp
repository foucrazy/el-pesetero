<%@ page import="es.elpesetero.security.OpenID" %>



<div class="fieldcontain ${hasErrors(bean: openIDInstance, field: 'url', 'error')} ">
	<label for="url">
		<g:message code="openID.url.label" default="Url" />
		
	</label>
	<g:textField name="url" value="${openIDInstance?.url}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: openIDInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="openID.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${es.elpesetero.security.SecurityUser.list()}" optionKey="id" required="" value="${openIDInstance?.user?.id}" class="many-to-one"/>
</div>

