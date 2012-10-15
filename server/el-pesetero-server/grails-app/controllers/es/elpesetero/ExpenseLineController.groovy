package es.elpesetero

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.converters.XML

class ExpenseLineController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [expenseLineInstanceList: ExpenseLine.list(params), expenseLineInstanceTotal: ExpenseLine.count()]
    }
	
	def listUser() {
		User user = session.user
		def expenseList = Expense.findAllByUser(user)
		def expenseLineInstance
		render(view: "list", model: [expenseLineInstanceList: expenseLineInstance, expenseLineInstanceTotal: ExpenseLine.count()])
	}

    def create() {
        [expenseLineInstance: new ExpenseLine(params), expenseInstance: new Expense()]
    }

    def save() {
		def expenseInstance = new Expense(params)
		if (!expenseInstance.save(flush: true)) {
			render(view: "create", model: [expenseLineInstance: expenseLineInstance])
			return
		}
		def expenseLineInstance = new ExpenseLine(expense:expenseInstance, expenseDate: params.expenseDate)
        if (!expenseLineInstance.save(flush: true)) {
            render(view: "create", model: [expenseLineInstance: expenseLineInstance, , expenseInstance: expenseInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'expenseLine.label', default: 'ExpenseLine'), expenseLineInstance.id])
        redirect(action: "show", id: expenseLineInstance.id)
    }

    def show() {
        def expenseLineInstance = ExpenseLine.get(params.id)
        if (!expenseLineInstance) {
			withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'expenseLine.label', default: 'ExpenseLine'), params.id])
					redirect(action: "list")					
				}
				json {
					render "error" as JSON
				}
			}
            return
        }

		withFormat {
			html {
				[expenseLineInstance: expenseLineInstance]
			}
			json {
				render expenseLineInstance as JSON
			}
		}
        
    }

    def edit() {
        def expenseLineInstance = ExpenseLine.get(params.id)
        if (!expenseLineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expenseLine.label', default: 'ExpenseLine'), params.id])
            redirect(action: "list")
            return
        }

        [expenseLineInstance: expenseLineInstance]
    }

    def update() {
        def expenseLineInstance = ExpenseLine.get(params.id)
        if (!expenseLineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expenseLine.label', default: 'ExpenseLine'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (expenseLineInstance.version > version) {
                expenseLineInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'expenseLine.label', default: 'ExpenseLine')] as Object[],
                          "Another user has updated this ExpenseLine while you were editing")
                render(view: "edit", model: [expenseLineInstance: expenseLineInstance])
                return
            }
        }

        expenseLineInstance.properties = params

        if (!expenseLineInstance.save(flush: true)) {
            render(view: "edit", model: [expenseLineInstance: expenseLineInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'expenseLine.label', default: 'ExpenseLine'), expenseLineInstance.id])
        redirect(action: "show", id: expenseLineInstance.id)
    }

    def delete() {
        def expenseLineInstance = ExpenseLine.get(params.id)
        if (!expenseLineInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'expenseLine.label', default: 'ExpenseLine'), params.id])
            redirect(action: "list")
            return
        }

        try {
            expenseLineInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'expenseLine.label', default: 'ExpenseLine'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'expenseLine.label', default: 'ExpenseLine'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
