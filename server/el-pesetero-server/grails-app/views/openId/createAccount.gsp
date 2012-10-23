<head>
<meta name='layout' content='main'/>
<title>Create Account</title>
</head>

<body>

<div class='body'>

	<h4><g:message code="openId.not.found.you.can.register" args="[command.email]" /></h4>

	<br/>

	<g:hasErrors bean="${command}">
	<div class="errors">
		<g:renderErrors bean="${command}" as="list"/>
	</div>
	</g:hasErrors>

	<g:if test='${flash.error}'>
	<div class="errors">${flash.error}</div>
	</g:if>

	<g:form action='createAccount'>

		<table>
		<tr>
			<td>Open ID:</td>
			<td><span id='openid'>${openId}</span></td>
		</tr>

		<tr>
			<td><label for='username'><g:message code="user.username.label" default="Username" />:</label></td>
			<td><g:textField name='username' value='${command.username}'/></td>
		</tr>

		<tr>
			<td><label for='email'>Email:</label></td>
			<td><g:textField name='email' value='${command.email}'/></td>
		</tr>		

		</table>

		<input type='submit' value='Register'/>

	</g:form>
	
	<g:if test='${openId}'>
	<br/>
	<g:message code="openId.you.can.link.init"/>
	<g:link action='linkAccount'><g:message code="openId.link.this.googleAccount"/></g:link>
	<g:message code="openId.you.can.link.end"/>
	</g:if>
</div>

</body>
