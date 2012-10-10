package es.elpesetero

class Expense {

    static constraints = {
		proRateType nullable: true
    }
	
	double quantity
	ExpenseCategory category
	Fund from
	FrequencyType proRateType
}
