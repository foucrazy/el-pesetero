package es.elpesetero

class ExpenseLineService {

    def addExpenseLine(ExpenseLine expenseLine) {
		def user = expenseLine.user
		Fund fromFund = expenseLine.expense.from
		log.info "Validating expenseLine"
		if (fromFund.user!=user)
			throw new UserPermissionException("Fund[$fromFund] does not belong to user[$user] in this expenseLine")
		if (fromFund.quantity<expenseLine.expense.quantity)
			throw new FundException("Fund[$fromFund] does not have enough quantity[$expenseLine.expense.quantity > fromFund.quantity]")
		log.info "Deducting money from Fund"
		fromFund.quantity-=expenseLine.expense.quantity
		log.info "Saving expense"
		expenseLine.expense.save(flush: true)
		log.info "Saving expenseLine"
		expenseLine.save(flush: true)
    }
}
