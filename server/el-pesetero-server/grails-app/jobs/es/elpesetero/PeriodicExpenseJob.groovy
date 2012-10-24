package es.elpesetero

import static es.elpesetero.FrequencyType.*

class PeriodicExpenseJob {
	
	def expenseService
	
    static triggers = {
      cron cronExpression: "0 0 9 * * ?"// execute job every day at 9
//		simple repeatInterval: 50000l // execute job every day
    }
	def group = "Expenses"

    def execute() {
		println "Ejecutamos PeriodicExpense"
		def all = PeriodicExpense.findAll {}
		println all
		all.each { PeriodicExpense pe ->
			if (pe.isPertinentDay()) 
				executeExpense(pe)
		}
    }
	
	def executeExpense(PeriodicExpense periodicExpense) {
		def user = periodicExpense.user
		println "We must execute this expense: $periodicExpense.expense for user $periodicExpense.user"
		try {
			periodicExpense.executeExpense()
		} catch (FundException exception) {
			println "Periodic expense not executed: $exception.message"
			notifyUser(user, exception.message)
		}
	}
	
	def notifyUser(user, message) {
		
	}
}
