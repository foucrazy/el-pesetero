package es.elpesetero

import es.elpesetero.security.SecurityUser;


class User {

	static hasMany = [funds: Fund, categories: ExpenseCategory]
    static constraints = {
		mail email: true		
    }
	
	def userService
	
	String username
	String mail
	SecurityUser securityUser
	
	def getTotalBalance() {
		funds.inject(0.0) { acc, it -> acc+it.quantity}
	}
	
	def getTopCategories() {		
		categories.grep{it.parent==null}
	}
	
	def withdrawCash(quantity) {
		userService.withdrawCash(this, quantity)
	}
	
	def withdrawCash(from, quantity) {
		userService.withdrawCash(this, from, quantity)
	}
	
	def withdrawCash(from, to, quantity) {
		userService.withdrawCash(this, from, to, quantity)
	}
	
	
}
