package es.elpesetero

class Fund {

	static belongsTo = [user: User]
    static constraints = {
    }
	
	User user
	String name
	double quantity
	FundType type
	
	def transferTo(Fund toFund, double transferQuantity) {
		validateFundForWithdraw(toFund.user, transferQuantity)		
		this.quantity-=transferQuantity
		toFund.quantity+=transferQuantity
	}
	
	public executeExpense(User user, double expenseQuantity) {		
		validateFundForWithdraw(user, expenseQuantity)		
		this.quantity-=expenseQuantity
	}
	
	public String toString() {
		"$name [$quantity]"
	}
	
	public Collection<ExpenseLine> getFundLastExpenses() {
		ExpenseLine.createCriteria().list (max:10) {
			expense {
				eq("from",this)				
			}
			order("expenseDate","desc")
		}.reverse()
	}
	
	private validateFundForWithdraw(User expenseUser, double expenseQuantity) {		
		if (this.user!=expenseUser)
			throw new UserPermissionException("Fund[$this] does not belong to user[$expenseUser]")
		if (this.quantity<expenseQuantity)
			throw new FundException("Fund[$this] does not have enough quantity:[$expenseQuantity > $quantity]")
	}

	
}
