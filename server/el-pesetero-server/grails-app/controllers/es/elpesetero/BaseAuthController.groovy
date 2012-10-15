package es.elpesetero

import es.elpesetero.security.SecurityUser;

class BaseAuthController {
	
	def User theUser
	
	def springSecurityService
	
	def beforeInterceptor = [action:this.&auth,except:'login']
	// defined as a regular method so its private
	def auth() {
		if (springSecurityService.isLoggedIn()) {
			SecurityUser securityUser = SecurityUser.findByUsername(springSecurityService.authentication.name)
			User user = User.findBySecurityUser(securityUser)
			theUser = user
			log.debug "Usuario $user.mail"
			return true
		} else {
			redirect(controller:'openId',action:'auth')
			return false
		}		 
	}

}
