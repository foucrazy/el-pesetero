package es.elpesetero

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.converters.XML
import grails.plugins.springsecurity.Secured;


@Secured(['ROLE_USER'])
class FundController extends BaseAuthController {
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST", withdrawCash: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def fundList = Fund.findAllByUser(theUser,params)
		withFormat {
			html {
				[fundInstanceList: fundList, fundInstanceTotal: Fund.findAllByUser(theUser).size()]
			}
			json {
				render fundList as JSON
			}
        }
    }

    def create() {
        [fundInstance: new Fund(params)]
    }

    def save() {
        def fundInstance = new Fund(params)
		fundInstance.user = theUser
        if (!fundInstance.save(flush: true)) {
			withFormat {
				html {
					render(view: "create", model: [fundInstance: fundInstance])
					return
				}
				json {
					render jsonError(fundInstance.errors)
					return
				}
			}
        }
		
		flash.message = message(code: 'default.created.message', args: [message(code: 'fund.label', default: 'Fund'), fundInstance.id])
		withFormat {
			html {
				redirect(action: "show", id: fundInstance.id)
			}
			json {
				render jsonSuccess(flash.message, "fund",fundInstance)
			}
		}
        
    }

    def show() {
        def fundInstance = Fund.get(params.id)
        if (!fundInstance) {
			withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'fund.label', default: 'Fund'), params.id])
					redirect(action: "list")					
				}
				json {
					render "error" as JSON
				}
			}			
            return
        }
		
		if (checkOwnership(fundInstance)) {				
			withFormat {
				html {
					[fundInstance: fundInstance]
				}
				json {
					render fundInstance as JSON
				}
			}
		}
        
    }

    def edit() {
        def fundInstance = Fund.get(params.id)
        if (!fundInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'fund.label', default: 'Fund'), params.id])
            redirect(action: "list")
            return
        }
		
		if (checkOwnership(fundInstance))
        	[ fundInstance: fundInstance]
    }

    def update() {
        def fundInstance = Fund.get(params.id)		
        if (!fundInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'fund.label', default: 'Fund'), params.id])
            redirect(action: "list")
            return
        }
		
		if (!checkOwnership(fundInstance))
			return

        if (params.version) {
            def version = params.version.toLong()
            if (fundInstance.version > version) {
                fundInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'fund.label', default: 'Fund')] as Object[],
                          "Another user has updated this Fund while you were editing")
                render(view: "edit", model: [ fundInstance: fundInstance])
                return
            }
        }

        fundInstance.properties = params

        if (!fundInstance.save(flush: true)) {
            render(view: "edit", model: [ fundInstance: fundInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'fund.label', default: 'Fund'), fundInstance.id])
        redirect(action: "show", id: fundInstance.id)
    }

    def delete() {
        def fundInstance = Fund.get(params.id)
        if (!fundInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'fund.label', default: 'Fund'), params.id])
            redirect(action: "list")
            return
        }

		if (checkOwnership(fundInstance)) {
			try {
				fundInstance.delete(flush: true)
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'fund.label', default: 'Fund'), params.id])
				redirect(action: "list")
			}
			catch (DataIntegrityViolationException e) {
				flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'fund.label', default: 'Fund'), params.id])
				redirect(action: "show", id: params.id)
			}
		}
    }
	
	def withdrawCash() {
		def fromFund = Fund.get(params.from)
		if (!params.quantity || params.double('quantity')<0) {
			def message = message(code: 'funds.withdrawCash.incorrect.quantity', args: [params.quantity])
			flash.message = message
			withFormat {
				html {
					redirect(action: "list")
				}
				json {					
					render jsonError(message)
				}
			}
			return			
		}
		
		def quantity = params.double('quantity')
		try {
			if (!fromFund) {
				theUser.withdrawCash(quantity)
			} else {
				theUser.withdrawCash(fromFund, quantity)
			}
		} catch (FundException e) {
			def message = message(code: 'funds.withdrawCash.fund.error', args: [e.message])
			flash.message = message 
			withFormat {
				html {
					redirect(action: "list")
				}
				json {
					render jsonError(message)
				}
			}			
			return
		}
		def message = message(code: 'funds.withdrawCash.correct', args: [params.quantity])
		flash.message = message
		withFormat {
			html {
				redirect(action: "list")
			} json {
				render jsonSuccess(message, quantity)
			}
		}
	}
	
	
	private jsonSuccess(message, quantity) {
		[success: message, quantity: quantity] as JSON
	}
}
