package es.elpesetero

class User {

	static hasMany = [funds: Fund, categories: ExpenseCategory]
    static constraints = {
		mail email: true
    }
	
	String username
	String mail
	
}
