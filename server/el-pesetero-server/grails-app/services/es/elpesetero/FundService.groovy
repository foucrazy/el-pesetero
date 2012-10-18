package es.elpesetero

class FundService {

    def transferMoney(Fund from, Fund to, double quantity) {
		from.transferTo(to, quantity)
		from.save()
		to.save()
    }
	
}
