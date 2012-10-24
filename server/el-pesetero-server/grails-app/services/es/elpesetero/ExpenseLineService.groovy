package es.elpesetero

class ExpenseLineService {

	def addExpenseLine(def user, def newExpense) {
		return addExpenseLine(new ExpenseLine(user: user, expense: newExpense, expenseDate:new Date()))
	}
	
    def addExpenseLine(ExpenseLine expenseLine) {
		log.info "Deducting expense"
		expenseLine.executeExpense()
		log.debug "Saving expense"
		def expense = expenseLine.expense.save()
		if (!expense) {
			log.error "Cannot save expenseLine: $expense.errors"
		}
		log.info "Saving expenseLine"
		if (!expenseLine.save(flush: true)) {
			log.error "Cannot save expenseLine: $expenseLine.errors"
			println  "Cannot save expenseLine: $expenseLine.errors"
			return null
		}
		return expenseLine		
    }
}
