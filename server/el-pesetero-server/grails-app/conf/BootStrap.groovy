import es.elpesetero.ExpenseCategory
import es.elpesetero.Expense
import es.elpesetero.ExpenseLine
import es.elpesetero.FrequencyType;
import es.elpesetero.PeriodicExpense
import es.elpesetero.User
import es.elpesetero.FundType
import es.elpesetero.Fund
import es.elpesetero.UserService
import es.elpesetero.security.SecurityUser
import es.elpesetero.security.Role
import es.elpesetero.security.UserRole
import grails.converters.JSON
import es.elpesetero.security.OpenID

class BootStrap {
	
	UserService userService

    def init = { servletContext ->
		JSON.registerObjectMarshaller(User) {
			it.properties.findAll {k,v -> 
				k != 'securityUser' && k != 'securityUserId' && k!= 'categories' && k != 'userService' 
			}
		}		
		JSON.registerObjectMarshaller(ExpenseCategory) {
			it.properties.findAll {k,v -> k!='user' && k!='userId' && k!='parent' && k!='parentId'}
		}
		JSON.registerObjectMarshaller(ExpenseLine) {
			def properties = it.properties.findAll {k,v -> k!='expenseId' && k!='user' && k!='expense'}
			properties << it.expense.properties << ['class': 'es.elpesetero.ExpenseLine']
			properties.each {k,v -> print k}
			properties
		}
		if (!User.count()) {
			ExpenseCategory vivienda = new ExpenseCategory(name: "Vivienda")
			vivienda.subCategories = [new ExpenseCategory(parent: vivienda, name: "Alquiler/Hipoteca")]
			vivienda = vivienda.save(failOnError: true)
			initSpringSecurity()
		}
		
    }
	
	def initSpringSecurity() {
		String password = 'password'
		def roleAdmin = new Role(authority: 'ROLE_ADMIN').save()
		def roleUser = new Role(authority: 'ROLE_USER').save()
		def admin = new SecurityUser(username: 'kortatu', password: password, enabled: true).save()		
		def openIdKortatu = new OpenID(url: "https://www.google.com/accounts/o8/id?id=AItOawl8ZHaySfc5NMiHaVUTsHNCEr133vT38II")
		admin.addToOpenIds(openIdKortatu)
		admin.save()
		UserRole.create admin, roleUser 
		UserRole.create admin, roleAdmin, true
		User kortatu = new User(username: 'kortatu', mail: "kortatu@gmail.com", securityUser: admin)
		kortatu = userService.addUser(kortatu);
		initFund(kortatu)
		addUser(password, roleUser)
		println kortatu.encodeAsJSON()
	}

	private addUser(String password, Role roleUser) {
		def user = new SecurityUser(username: 'agonzalez', password: password, enabled: true).save()
		def openIdAgonzalez = new OpenID(url: "https://www.google.com/accounts/o8/id?id=AItOawmffd3FFhgl6ffmsncINHn55m8MckIhtE0")
		user.addToOpenIds(openIdAgonzalez)
		user.save()
		UserRole.create user, roleUser
		userService.addUser(new User(username: 'agonzalez', mail:'agonzalez@germinus.com', securityUser: user))
	}

	private initFund(User kortatu) {
		Fund fund = kortatu.funds.find()
		println "Fund $fund"
		fund.quantity = 20
		fund.save(flush: true)
	}

	
    def destroy = {
    }
}
