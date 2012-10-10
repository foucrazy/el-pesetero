package es.elpesetero

class ExpenseCategory {

	static hasMany = [subCategories: ExpenseCategory]
	static belongsTo = [parent: ExpenseCategory]
    static constraints = {
		parent nullable: true
		user nullable: true
    }
	
	ExpenseCategory parent
	String name
	User user
	
	public String toString() {
		name
	}
}
