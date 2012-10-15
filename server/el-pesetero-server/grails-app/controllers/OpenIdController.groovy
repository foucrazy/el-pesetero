import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler as OIAFH

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.savedrequest.DefaultSavedRequest

import es.elpesetero.security.SecurityUser
import es.elpesetero.security.Role
import es.elpesetero.security.UserRole
import es.elpesetero.User;
import es.elpesetero.UserService
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.context.SecurityContextHolder as SCH

/**
 * Manages associating OpenIDs with application users, both by creating a new local user
 * associated with an OpenID and also by associating a new OpenID to an existing account.
 */
class OpenIdController {

	/** Dependency injection for daoAuthenticationProvider. */
	def daoAuthenticationProvider

	/** Dependency injection for OpenIDAuthenticationFilter. */
	def openIDAuthenticationFilter

	/** Dependency injection for the springSecurityService. */
	def springSecurityService
	
	/** Dependency injection for the UserService. */
	UserService userService

	static defaultAction = 'auth'

	/**
	 * Shows the login page. The user has the choice between using an OpenID and a username
	 * and password for a local account. If an OpenID authentication is successful but there
	 * is no corresponding local account, they'll be redirected to createAccount to create
	 * a new account, or click through to linkAccount to associate the OpenID with an
	 * existing local account.
	 */
	def auth = {

		def config = SpringSecurityUtils.securityConfig

		if (springSecurityService.isLoggedIn()) {
			redirect uri: config.successHandler.defaultTargetUrl
			return
		}

		[openIdPostUrl: "${request.contextPath}$openIDAuthenticationFilter.filterProcessesUrl",
		 daoPostUrl:    "${request.contextPath}${config.apf.filterProcessesUrl}",
		 persistentRememberMe: config.rememberMe.persistent,
		 rememberMeParameter: config.rememberMe.parameter,
		 openidIdentifier: config.openid.claimedIdentityFieldName]
	}

	/**
	 * Initially we're redirected here after a UserNotFoundException with a valid OpenID
	 * authentication. This action is specified by the openid.registration.createAccountUri
	 * attribute.
	 * <p/>
	 * The GSP displays the OpenID that was received by the external provider and keeps it
	 * in the session rather than passing it between submits so the user has no opportunity
	 * to change it.
	 */
	def createAccount = { OpenIdRegisterCommand command ->

//		def config = SpringSecurityUtils.securityConfig
//		
//		String openId = session[OIAFH.LAST_OPENID_USERNAME]
//		println "Open Id $openId" 
//		if (!openId) { 
//			flash.error = 'Sorry, an OpenID was not found'
//			redirect uri: config.failureHandler.defaultFailureUrl 
//			return 
//		}
//		
//		def user = new GrailsUser(openId, 'password', true, true, true, true, [new GrantedAuthorityImpl('ROLE_OPENID')], 0)
//		println "GrailUser $user"
//		println "Authorities $user.authorities"
//		SCH.context.authentication = new UsernamePasswordAuthenticationToken( user, 'password', user.authorities)
//		
//		session.removeAttribute OIAFH.LAST_OPENID_USERNAME
//		session.removeAttribute OIAFH.LAST_OPENID_ATTRIBUTES
//		
//		def savedRequest = session[DefaultSavedRequest.SPRING_SECURITY_SAVED_REQUEST_KEY] 
//		if (savedRequest && !config.successHandler.alwaysUseDefault) {
//			println "Hay savedRequest $savedRequest.redirectUrl"
//			redirect url: savedRequest.redirectUrl 
//		} else { 
//			println "La url por defeecto de success es $config.successHandler.defaultTargetUrl"
//			redirect uri: config.successHandler.defaultTargetUrl 
//		}
//
//	}
			String openId = session[OIAFH.LAST_OPENID_USERNAME]
			println "El openId es $openId"
			if (!openId) {
				flash.error = 'Sorry, an OpenID was not found'
				return [command: command]
			}
	
			if (!request.post) {
				// show the form
				command.clearErrors()
				copyFromAttributeExchange command
				return [command: command, openId: openId]
			}
	
			if (command.hasErrors()) {
				return [command: command, openId: openId]
			}
	
			if (!createNewAccount(command.username, command.email, openId)) {
				return [command: command, openId: openId]
			}
	
			authenticateAndRedirect command.username
	}
	
	/**
	 * The registration page has a link to this action so an existing user who successfully
	 * authenticated with an OpenID can associate it with their account for future logins.
	 */
	def linkAccount = { OpenIdLinkAccountCommand command ->

		String openId = session[OIAFH.LAST_OPENID_USERNAME]
		if (!openId) {
			flash.error = 'Sorry, an OpenID was not found'
			return [command: command]
		}

		if (!request.post) {
			// show the form
			command.clearErrors()
			return [command: command, openId: openId]
		}

		if (command.hasErrors()) {
			return [command: command, openId: openId]
		}

		try {
			registerAccountOpenId command.username, command.password, openId
		}
		catch (AuthenticationException e) {
			flash.error = 'Sorry, no user was found with that username and password'
			return [command: command, openId: openId]
		}

		authenticateAndRedirect command.username
	}

	/**
	 * Authenticate the user for real now that the account exists/is linked and redirect
	 * to the originally-requested uri if there's a SavedRequest.
	 *
	 * @param username the user's login name
	 */
	protected void authenticateAndRedirect(String username) {
		session.removeAttribute OIAFH.LAST_OPENID_USERNAME
		session.removeAttribute OIAFH.LAST_OPENID_ATTRIBUTES

		springSecurityService.reauthenticate username

		def config = SpringSecurityUtils.securityConfig

		def savedRequest = session[DefaultSavedRequest.SPRING_SECURITY_SAVED_REQUEST_KEY]
		if (savedRequest && !config.successHandler.alwaysUseDefault) {
			redirect url: savedRequest.redirectUrl
		}
		else {
			redirect uri: config.successHandler.defaultTargetUrl
		}
	}

	/**
	 * Create the user instance and grant any roles that are specified in the config
	 * for new users.
	 * @param username  the username
	 * @param password  the password
	 * @param openId  the associated OpenID
	 * @return  true if successful
	 */
	protected boolean createNewAccount(String username, String email, String openId) {
		boolean created = SecurityUser.withTransaction { status ->
			def config = SpringSecurityUtils.securityConfig

			def password = encodePassword('kakakak')
			def securityUser = new SecurityUser(username: username, password: password, enabled: true)

			securityUser.addToOpenIds(url: openId)

			if (!securityUser.save()) {
				return false
			}					
			
			User user = new User(username: username, mail: email, securityUser: securityUser)
			user = userService.addUser(user)
			
			for (roleName in config.openid.registration.roleNames) {
				UserRole.create securityUser, Role.findByAuthority(roleName)
			}
			return true
		}
		return created
	}

	protected String encodePassword(String password) {
		def config = SpringSecurityUtils.securityConfig
		def encode = config.openid.encodePassword
		if (!(encode instanceof Boolean) || (encode instanceof Boolean && encode)) {
			password = springSecurityService.encodePassword(password)
		}
		password
	}

	/**
	 * Associates an OpenID with an existing account. Needs the user's password to ensure
	 * that the user owns that account, and authenticates to verify before linking.
	 * @param username  the username
	 * @param password  the password
	 * @param openId  the associated OpenID
	 */
	protected void registerAccountOpenId(String username, String password, String openId) {
		// check that the user exists, password is valid, etc. - doesn't actually log in or log out,
		// just checks that user exists, password is valid, account not locked, etc.
		daoAuthenticationProvider.authenticate(
				new UsernamePasswordAuthenticationToken(username, password))

		SecurityUser.withTransaction { status ->
			def user = SecurityUser.findByUsername(username)
			user.addToOpenIds(url: openId)
			if (!user.validate()) {
				status.setRollbackOnly()
			}
		}
	}

	/**
	 * For the initial form display, copy any registered AX values into the command.
	 * @param command  the command
	 */
	protected void copyFromAttributeExchange(OpenIdRegisterCommand command) {
		List attributes = session[OIAFH.LAST_OPENID_ATTRIBUTES] ?: []
		for (attribute in attributes) {
			// TODO document
			String name = attribute.name
			if (command.hasProperty(name)) {
				println "Copying $name"
				command."$name" = attribute.values[0]
			}
		}
	}
}

class OpenIdRegisterCommand {

	String email = ""
	String username = ""

	static constraints = {
		username blank: false, validator: { String username, command ->
			SecurityUser.withNewSession { session ->
				if (username && SecurityUser.countByUsername(username)) {
					return 'openIdRegisterCommand.username.error.unique'
				}
			}
		}
		email blank: false, minSize: 8, maxSize: 64, mail: true, validator: { email, command ->
			SecurityUser.withNewSession { session ->
				if (email && User.countByMail(email)) {
					return 'openIdRegisterCommand.email.error.unique'
				}
			}			
		}
		
	}
}

class OpenIdLinkAccountCommand {

	String username = ""
	String password = ""

	static constraints = {
		username blank: false
		password blank: false
	}
}

