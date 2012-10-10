package es.elpesetero

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.converters.XML

class ExpenseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [expenseInstanceList: Expense.list(params), expenseInstanceTotal: Expense.count()]
    }

    def create() {
        [expenseInstance: new Expense(params)]
    }

    def save() {
        def expenseInstance = new Expense(params)
        if (!expenseInstance.save(flush: true)) {
            render(view: "create", model: [expenseInstance: expenseInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'expense.label', default: 'Expense'), expenseInstance.id])
        redirect(action: "show", id: expenseInstance.id)
    }

    def show() {
        def expenseInstance = Expense.get(params.id)		
        if (!expenseInstance) {
			withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'expense.label', default: 'Expense'), params.id])
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
				[expenseInstance: expenseInstance]
			}
			json {
				render expenseInstance as JSON
			}
		}
        
    }

    def edit() {
        def expenseInstance = Expense.get(params.id)
        if (!expenseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expense.label', default: 'Expense'), params.id])
            redirect(action: "list")
            return
        }

        [expenseInstance: expenseInstance]
    }

    def update() {
        def expenseInstance = Expense.get(params.id)
        if (!expenseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expense.label', default: 'Expense'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (expenseInstance.version > version) {
                expenseInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'expense.label', default: 'Expense')] as Object[],
                          "Another user has updated this Expense while you were editing")
                render(view: "edit", model: [expenseInstance: expenseInstance])
                return
            }
        }

        expenseInstance.properties = params

        if (!expenseInstance.save(flush: true)) {
            render(view: "edit", model: [expenseInstance: expenseInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'expense.label', default: 'Expense'), expenseInstance.id])
        redirect(action: "show", id: expenseInstance.id)
    }

    def delete() {
        def expenseInstance = Expense.get(params.id)
        if (!expenseInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'expense.label', default: 'Expense'), params.id])
            redirect(action: "list")
            return
        }

        try {
            expenseInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'expense.label', default: 'Expense'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'expense.label', default: 'Expense'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
