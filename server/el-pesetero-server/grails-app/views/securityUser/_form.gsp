<%@ page import="es.elpesetero.security.SecurityUser" %>



<div class="fieldcontain ${hasErrors(bean: securityUserInstance, field: 'username', 'error')} required">
	<label for="username">
		<g:message code="securityUser.username.label" default="Username" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="username" required="" value="${securityUserInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: securityUserInstance, field: 'newPassword', 'error')}">
	<label for="newPassword">
		<g:message code="securityUser.newPassword.label" default="Nueva Password" />		
	</label>
	<g:passwordField name="newPassword" required="" value="${newPassword}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: securityUserInstance, field: 'newPasswordConfirm', 'error')}">
	<label for="newPasswordConfirm">
		<g:message code="securityUser.newPasswordConfirm.label" default="Nueva Password" />		
	</label>
	<g:passwordField name="newPasswordConfirm" required="" value="${newPasswordConfirm}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: securityUserInstance, field: 'accountExpired', 'error')} ">
	<label for="accountExpired">
		<g:message code="securityUser.accountExpired.label" default="Account Expired" />
		
	</label>
	<g:checkBox name="accountExpired" value="${securityUserInstance?.accountExpired}" />
</div>

<div class="fieldcontain ${hasErrors(bean: securityUserInstance, field: 'accountLocked', 'error')} ">
	<label for="accountLocked">
		<g:message code="securityUser.accountLocked.label" default="Account Locked" />
		
	</label>
	<g:checkBox name="accountLocked" value="${securityUserInstance?.accountLocked}" />
</div>

<div class="fieldcontain ${hasErrors(bean: securityUserInstance, field: 'enabled', 'error')} ">
	<label for="enabled">
		<g:message code="securityUser.enabled.label" default="Enabled" />
		
	</label>
	<g:checkBox name="enabled" value="${securityUserInstance?.enabled}" />
</div>

<div class="fieldcontain ${hasErrors(bean: securityUserInstance, field: 'openIds', 'error')} ">
	<label for="openIds">
		<g:message code="securityUser.openIds.label" default="Open Ids" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${securityUserInstance?.openIds?}" var="o">
    <li><g:link controller="openID" action="show" id="${o.id}">${o?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="openID" action="create" params="['securityUser.id': securityUserInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'openID.label', default: 'OpenID')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: securityUserInstance, field: 'passwordExpired', 'error')} ">
	<label for="passwordExpired">
		<g:message code="securityUser.passwordExpired.label" default="Password Expired" />
		
	</label>
	<g:checkBox name="passwordExpired" value="${securityUserInstance?.passwordExpired}" />
</div>

