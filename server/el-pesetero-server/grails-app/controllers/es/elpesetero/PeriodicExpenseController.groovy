package es.elpesetero

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.converters.XML

class PeriodicExpenseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [periodicExpenseInstanceList: PeriodicExpense.list(params), periodicExpenseInstanceTotal: PeriodicExpense.count()]
    }

    def create() {
        [periodicExpenseInstance: new PeriodicExpense(params), expenseInstance: new Expense(params)]
    }

    def save() {
		def expenseInstance = new Expense(params)
		if (!expenseInstance.save(flush: true)) {
			render(view: "create", model: [expenseInstance: expenseInstance])
			return
		}
        def periodicExpenseInstance = new PeriodicExpense(expense: expenseInstance, frequency: params.frequency, day:params.day)
        if (!periodicExpenseInstance.save(flush: true)) {
            render(view: "create", model: [periodicExpenseInstance: periodicExpenseInstance, expenseInstance: expenseInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'periodicExpense.label', default: 'PeriodicExpense'), periodicExpenseInstance.id])
        redirect(action: "show", id: periodicExpenseInstance.id)
    }

    def show() {
        def periodicExpenseInstance = PeriodicExpense.get(params.id)
        if (!periodicExpenseInstance) {
			withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodicExpense.label', default: 'PeriodicExpense'), params.id])
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
				[periodicExpenseInstance: periodicExpenseInstance]
			}
			json {
				render periodicExpenseInstance as JSON
			}
		}
        
    }

    def edit() {
        def periodicExpenseInstance = PeriodicExpense.get(params.id)
        if (!periodicExpenseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodicExpense.label', default: 'PeriodicExpense'), params.id])
            redirect(action: "list")
            return
        }

        [periodicExpenseInstance: periodicExpenseInstance]
    }

    def update() {
        def periodicExpenseInstance = PeriodicExpense.get(params.id)
        if (!periodicExpenseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodicExpense.label', default: 'PeriodicExpense'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (periodicExpenseInstance.version > version) {
                periodicExpenseInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'periodicExpense.label', default: 'PeriodicExpense')] as Object[],
                          "Another user has updated this PeriodicExpense while you were editing")
                render(view: "edit", model: [periodicExpenseInstance: periodicExpenseInstance])
                return
            }
        }

        periodicExpenseInstance.properties = params

        if (!periodicExpenseInstance.save(flush: true)) {
            render(view: "edit", model: [periodicExpenseInstance: periodicExpenseInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'periodicExpense.label', default: 'PeriodicExpense'), periodicExpenseInstance.id])
        redirect(action: "show", id: periodicExpenseInstance.id)
    }

    def delete() {
        def periodicExpenseInstance = PeriodicExpense.get(params.id)
        if (!periodicExpenseInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodicExpense.label', default: 'PeriodicExpense'), params.id])
            redirect(action: "list")
            return
        }

        try {
            periodicExpenseInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'periodicExpense.label', default: 'PeriodicExpense'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'periodicExpense.label', default: 'PeriodicExpense'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
