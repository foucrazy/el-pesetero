package es.elpesetero

class Expense {

    static constraints = {
		proRateType nullable: true
    }
	
	String name
	double quantity
	ExpenseCategory category
	Fund from
	FrequencyType proRateType
	
	def executeExpense(User user) {
		from.executeExpense(user, quantity)
	}
}
