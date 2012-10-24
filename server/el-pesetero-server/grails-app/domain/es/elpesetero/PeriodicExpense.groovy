package es.elpesetero

import static es.elpesetero.FrequencyType.*

class PeriodicExpense {
	
	def expenseLineService

    static constraints = {
    }
	
	Expense expense
	FrequencyType frequency
	int day
	User user
	
	def isPertinentDay() {
		if (frequency==dayly) return true;
		frequency.pertinentDay == day
	}
	
	def executeExpense() {
		def today = new Date()
		def newExpense = new Expense(expense.properties)
		newExpense.name = "$expense.name de $today"
		def expenseLine = expenseLineService.addExpenseLine(user, newExpense)
		if (expenseLine) {
			log.info "Periodic expense $expense executed for $today"
			println "Periodic expense $expense executed for $today"
			expenseLine
		} else {
			println "Cannot execute periodic expense $newExpense.errors"
			log.error "Cannot execute periodic expense $newExpense.errors"
			return null
		}		
		
	}
	
}
