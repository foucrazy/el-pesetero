package es.elpesetero

class Expense {

    static constraints = {
		proRateType nullable: true
		category nullable: true
    }
	
	String name
	double quantity
	ExpenseCategory category
	Fund from
	FrequencyType proRateType
	
	def executeExpense(User user) {
		from.executeExpense(user, quantity)
	}
	
	public String toString() {
		"$name : $quantity € en categoría $category de $from"
	}
}
