<head>
	<meta name='layout' content='main'/>
	<title><g:message code="springSecurity.login.title"/></title>
<style type='text/css' media='screen'>
	#login {
		margin: 15px 0px;
		padding: 0px;
		text-align: center;
	}

	#login .inner {
		width: 340px;
		padding-bottom: 6px;
		margin: 60px auto;
		text-align: left;
		border: 1px solid #aab;
		background-color: #f0f0fa;
		-moz-box-shadow: 2px 2px 2px #eee;
		-webkit-box-shadow: 2px 2px 2px #eee;
		-khtml-box-shadow: 2px 2px 2px #eee;
		box-shadow: 2px 2px 2px #eee;
	}

	#login .inner .fheader {
		padding: 18px 26px 14px 26px;
		background-color: #f7f7ff;
		margin: 0px 0 14px 0;
		color: #2e3741;
		font-size: 18px;
		font-weight: bold;
	}

	#login .inner .cssform p {
		clear: left;
		margin: 0;
		padding: 4px 0 3px 0;
		padding-left: 105px;
		margin-bottom: 20px;
		height: 1%;
	}

	#login .inner .cssform input[type='text'] {
		width: 120px;
	}

	#login .inner .cssform label {
		font-weight: bold;
		float: left;
		text-align: right;
		margin-left: -105px;
		width: 110px;
		padding-top: 3px;
		padding-right: 10px;
	}

	#login #remember_me_holder {
		padding-left: 120px;
	}

	#login #submit {
		margin-left: 15px;
	}

	#login #remember_me_holder label {
		float: none;
		margin-left: 0;
		text-align: left;
		width: 200px
	}

	#login .inner .login_message {
		padding: 6px 25px 20px 25px;
		color: #c33;
	}

	#login .inner .text_ {
		width: 120px;
	}

	#login .inner .chk {
		height: 12px;
	}

	div.openid-loginbox {
		width: 450px;
		margin-left: auto;
		margin-right: auto;
		background: white;
		padding: 15px;
	}

	.openid-loginbox-inner {
		width: 450px;
		border: 1px blue solid;
	}

	td.openid-loginbox-title {
		background: #e0e0ff;
		border-bottom: 1px #c0c0ff solid;
		padding: 0;
	}
	
	td.openid-loginbox-title table {
		width: 100%;
		font-size: 18px;
	}
	.openid-loginbox-useopenid {
		font-weight: normal;
		font-size: 14px;
	}
	td.openid-loginbox-title img {
		border: 0;
		vertical-align: middle;
		padding-right: 3px;
	}
	table.openid-loginbox-userpass {
		margin: 3px 3px 3px 8px;
	}
	table.openid-loginbox-userpass td {
		height: 25px;
	}
	input.openid-identifier {
		background: url(http://stat.livejournal.com/img/openid-inputicon.gif) no-repeat;
		background-color: #fff;
		background-position: 0 50%;
		padding-left: 18px;
	}
	
	input[type='text'],input[type='password'] {
		font-size: 16px;
		width: 310px;
	}
	input[type='submit'] {
		font-size: 14px;
	}
	
	td.openid-submit {
		padding: 3px;
	}

</style>
</head>

<body>

<div class="openid-loginbox">
	<div class='inner'>
		<div class='fheader'><g:message code="springSecurity.login.header"/></div>
		<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
		</g:if>

	<table class='openid-loginbox-inner' cellpadding="0" cellspacing="0">
		
		<tr>
			<td>

			<div id='openidLogin'>
				<form action='${openIdPostUrl}' method='POST' autocomplete='off' name='openIdLoginForm'>
				<input type="hidden" name="${openidIdentifier}" class="openid-identifier" value="https://www.google.com/accounts/o8/id"/>
				<p id="remember_me_holder">
					<input type='checkbox' name='${rememberMeParameter}' id='remember_me'/>
					<label for='remember_me'>Remember me</label>
				</p>
				<p>
				<!-- <input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}'/>-->
					<input type='submit' id="submit" value='Login con Google'/>
				</p>
				<!--  table class="openid-loginbox-userpass">
					<tr>
						<td colspan='2' class="openid-submit" align="center">
							<input type="submit" value="Log in con Google" />
						</td>
					</tr>
					<g:if test='${persistentRememberMe}'>
					<tr>
						<td><label for='remember_me'>Remember me</label></td>
						<td>
							<input type='checkbox' name='${rememberMeParameter}' id='remember_me'/>
						</td>
					</tr>
					</g:if>
				</table-->
				</form>
			</div>

			<div id='formLogin' style='display: none'>
				<form action='${daoPostUrl}' method='POST' autocomplete='off'>
				<table class="openid-loginbox-userpass">
					<tr>
						<td>Username:</td>
						<td><input type="text" name='j_username' id='username' /></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type="password" name='j_password' id='password' /></td>
					</tr>
					<tr>
						<td><label for='remember_me'>Remember me</label></td>
						<td>
							<input type='checkbox' name='${rememberMeParameter}' id='remember_me'/>
						</td>
					</tr>
					<tr>
						<td colspan='2' class="openid-submit" align="center">
							<input type="submit" value="Log in" />
						</td>
					</tr>
				</table>
				</form>
			</div>

			</td>
		</tr>
	</table>
	</div>
</div>

<script>

(function() { document.forms['openIdLoginForm'].elements['openid_identifier'].focus(); })();

var openid = true;

function toggleForms() {
	if (openid) {
		document.getElementById('openidLogin').style.display = 'none';
		document.getElementById('formLogin').style.display = '';
	}
	else {
		document.getElementById('openidLogin').style.display = '';
		document.getElementById('formLogin').style.display = 'none';
	}
	openid = !openid;
}
</script>
</body>
