package es.elpesetero

import es.elpesetero.security.SecurityUser;

class BaseAuthController {
	
	def User theUser
	
	def springSecurityService
	
	def beforeInterceptor = [action:this.&auth,except:'login']
	
	def afterInterceptor = { model ->
		model.theUser = theUser
	}
	// defined as a regular method so its private
	def auth() {
		if (springSecurityService.isLoggedIn()) {
			SecurityUser securityUser = SecurityUser.findByUsername(springSecurityService.authentication.name)
			User user = User.findBySecurityUser(securityUser)
			theUser = user
			log.debug "Usuario $user.mail"
			return true
		} else {
			redirect(controller:'login',action:'auth')
			return false
		}		 
	}
	
	
	private boolean checkOwnership(domain) {
		if (domain.user) 
			if (domain.user != theUser) {
				redirect(controller: "login", action: "denied")
				return false
			}
		return true
	}

}
