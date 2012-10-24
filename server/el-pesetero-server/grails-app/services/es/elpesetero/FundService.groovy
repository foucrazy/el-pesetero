package es.elpesetero

class FundService {

    def transferMoney(Fund from, Fund to, double quantity) {
		log.info "Executing transfer"
		from.transferTo(to, quantity)
		log.debug "Saving"
		from.save()
		to.save()
		log.debug "Saved"
    }
	
}
