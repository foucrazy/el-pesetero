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
}
