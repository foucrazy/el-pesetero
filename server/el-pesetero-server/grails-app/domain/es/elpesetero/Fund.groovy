package es.elpesetero

class Fund {

	static belongsTo = [user: User]
    static constraints = {
    }
	
	User user
	String name
	double quantity
	FundType type
	
	def transferTo = { Fund toFund, double transferQuantity ->
		if (toFund.user != user)
			throw new RuntimeException("Funds user are not the same!")
		if (quantity<transferQuantity)
			throw new Exception("Transfered quantity is higher than curent funds")
		this.quantity-=transferQuantity
		toFund.quantity+=transferQuantity
	}
	
	public String toString() {
		name
	}
}
