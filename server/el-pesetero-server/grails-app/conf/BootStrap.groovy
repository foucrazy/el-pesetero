import es.elpesetero.*;
import grails.converters.JSON

class BootStrap {
	
	UserService userService

    def init = { servletContext ->		
		if (!User.count()) {
			ExpenseCategory vivienda = new ExpenseCategory(name: "Vivienda")
			vivienda.subCategories = [new ExpenseCategory(parent: vivienda, name: "Alquiler/Hipoteca")]
			vivienda = vivienda.save(failOnError: true)
			println "Vivienda subcategories: $vivienda.subCategories"
			User kortatu = new User(username: 'kortatu', mail: "kortatu@gmail.com");
			kortatu = userService.addUser(kortatu);
			def onlyParentCategories = kortatu.categories.grep({it.parent==null})
			println "Categories parent de kortatu $onlyParentCategories"
			new Fund(user: kortatu, name: "Cuenta ahorro", type: FundType.bankAccount, quantity: 3100).save(failOnError: true)
		}
		
    }
	
    def destroy = {
    }
}
