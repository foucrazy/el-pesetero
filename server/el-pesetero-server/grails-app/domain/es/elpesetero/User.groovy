package es.elpesetero

import es.elpesetero.security.SecurityUser;

class User {

	static hasMany = [funds: Fund, categories: ExpenseCategory]
    static constraints = {
		mail email: true
    }
	
	String username
	String mail
	SecurityUser securityUser
	
}
