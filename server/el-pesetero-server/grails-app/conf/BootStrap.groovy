import es.elpesetero.ExpenseCategory
import es.elpesetero.User
import es.elpesetero.FundType
import es.elpesetero.Fund
import es.elpesetero.UserService
import es.elpesetero.security.SecurityUser
import es.elpesetero.security.Role
import es.elpesetero.security.UserRole
import grails.converters.JSON

class BootStrap {
	
	UserService userService

    def init = { servletContext ->		
		if (!User.count()) {
			SecurityUser admin = initSpringSecurity()
			ExpenseCategory vivienda = new ExpenseCategory(name: "Vivienda")
			vivienda.subCategories = [new ExpenseCategory(parent: vivienda, name: "Alquiler/Hipoteca")]
			vivienda = vivienda.save(failOnError: true)
			println "Vivienda subcategories: $vivienda.subCategories"
			User kortatu = new User(username: 'kortatu', mail: "kortatu@gmail.com", securityUser: admin);
			kortatu = userService.addUser(kortatu);
			def onlyParentCategories = kortatu.categories.grep({it.parent==null})
			new Fund(user: kortatu, name: "Cuenta ahorro", type: FundType.bankAccount, quantity: 3100).save(failOnError: true)
		}
		
    }
	
	def initSpringSecurity() {
		String password = 'password'
		def roleAdmin = new Role(authority: 'ROLE_ADMIN').save()
		def roleUser = new Role(authority: 'ROLE_USER').save()
		def user = new SecurityUser(username: 'user', password: password, enabled: true).save()
		def admin = new SecurityUser(username: 'admin', password: password, enabled: true).save()		
		UserRole.create user, roleUser 
		UserRole.create admin, roleUser 
		UserRole.create admin, roleAdmin, true
		admin
	}
	
    def destroy = {
    }
}
