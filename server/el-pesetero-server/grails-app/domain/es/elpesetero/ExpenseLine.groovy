package es.elpesetero

class ExpenseLine {

    static constraints = {		
    }
	
	Date expenseDate
	Expense expense
	User user


	public executeExpense() {
		expense.executeExpense(user)
	}
	
	public String toString() {
		"$expense día: ${String.format('%te/%<tB/%<tY', expenseDate)}"
	}	
}
