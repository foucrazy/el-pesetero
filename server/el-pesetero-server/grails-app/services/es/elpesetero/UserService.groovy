package es.elpesetero

class UserService {
	
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
		user.save(failOnError: true)
    }
}
