<head>
<meta name='layout' content='main'/>
<title>Link Account</title>
</head>

<body>

<div class='body'>

	<h4><g:message code="openId.link.account.enter.username.and.password"/></h4>

	<g:hasErrors bean="${command}">
	<div class="errors">
		<g:renderErrors bean="${command}" as="list"/>
	</div>
	</g:hasErrors>

	<g:if test='${flash.error}'>
	<div class="errors">${flash.error}</div>
	</g:if>

	<g:if test='${flash.successMessage}'>
	${flash.successMessage}
	</g:if>

	<g:else>

	<g:form action='linkAccount'>

		<table>
		<tr>
			<td>Open ID:</td>
			<td><span id='openid'>${openId}</span></td>
		</tr>

		<tr>
			<td><label for='username'><g:message code="user.username.label" default="Username" />:</label></td>
			<td><g:textField name='username' value='${command?.username}'/></td>
		</tr>

		<tr>
			<td><label for='password'><g:message code="user.password.label" default="Password" />:</label></td>
			<td><g:passwordField name='password' value='${command?.password}'/></td>
		</tr>

		</table>

		<input type='submit' value='Link'/>

	</g:form>

	</g:else>

</div>

<script>
(function() { document.getElementById('username').focus(); })();
</script>

</body>
