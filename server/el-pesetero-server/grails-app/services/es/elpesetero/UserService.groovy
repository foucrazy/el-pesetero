package es.elpesetero

import es.elpesetero.security.SecurityUser;

class UserService {
	
	def fundService
	def springSecurityService
	
	def checkAuthUser() {
		User user
		if (springSecurityService.isLoggedIn()) {
			SecurityUser securityUser = SecurityUser.findByUsername(springSecurityService.authentication.name)
			user = User.findBySecurityUser(securityUser)
			println "Usuario $user.mail"
		}
		return user
	}
	
    def addUser(User user) {
		def categories =  ExpenseCategory.findAllByUserAndParent(null,null)
		user.categories = []
		categories.each {
			def userEC = new ExpenseCategory(name: it.name, parent: null, user:user, subCategories: [])
			println ("Creating category $it.name for new user $user.username")
			it.subCategories.each { subIt ->
				userEC.subCategories.add(new ExpenseCategory(name: subIt.name, parent: userEC, user:user))
				println"Creating subcategory $subIt.name with parent category $it.name for user $user.username"
			}
			user.categories << userEC
		}
		user.funds = []
		def fund = new Fund (user: user, name: 'Efectivo', type: FundType.cash)
		user.funds << fund 
		user.save(failOnError: true)
    }
	
	
	def withdrawCash(User user, quantity) {
		def cashFund = Fund.findAllByUserAndType(user,FundType.cash)[0]
		if (!cashFund)
			throw new FundException("User hasn't got any cash account to withdraw money to")
		def bankFund = Fund.findAllByUserAndType(user,FundType.bankAccount)[0]
		if (!bankFund)
			throw new FundException("User hasn't got any bank account to withdraw money from")
		fundService.transferMoney(bankFund, cashFund, quantity)
	}
	
	def withdrawCash(User user, from, quantity) {
		if (from.user==user) {
			def cashFund = Fund.findAllByUserAndType(user,FundType.cash)[0]
			fundService.transferMoney(from, cashFund, quantity)
		} else throw new UserPermissionException("Cannot withdraw cash from another user account")
	}
	
	def withdrawCash(User user, from, to, quantity) {
		if (from.user==user && from.user==user) {
			fundService.transferMoney(from, to, quantity)
		} else throw new UserPermissionException("Cannot withdraw cash from/to another user account")
	}
}
