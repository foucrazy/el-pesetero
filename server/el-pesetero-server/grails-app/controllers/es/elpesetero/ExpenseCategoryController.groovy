package es.elpesetero

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.converters.XML

class ExpenseCategoryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [expenseCategoryInstanceList: ExpenseCategory.list(params), expenseCategoryInstanceTotal: ExpenseCategory.count()]
    }

    def create() {
        [expenseCategoryInstance: new ExpenseCategory(params)]
    }

    def save() {
        def expenseCategoryInstance = new ExpenseCategory(params)
        if (!expenseCategoryInstance.save(flush: true)) {
            render(view: "create", model: [expenseCategoryInstance: expenseCategoryInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'expenseCategory.label', default: 'ExpenseCategory'), expenseCategoryInstance.id])
        redirect(action: "show", id: expenseCategoryInstance.id)
    }

    def show() {
        def expenseCategoryInstance = ExpenseCategory.get(params.id)
        if (!expenseCategoryInstance) {
			withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'expenseCategory.label', default: 'ExpenseCategory'), params.id])
					redirect(action: "list")
				}
				json {
					render "errror" as JSON
				}
			}
            return
        }

		withFormat {
			html {
				[expenseCategoryInstance: expenseCategoryInstance]
			}
			json {
				render expenseCategoryInstance as JSON
			}			
		}
        
    }

    def edit() {
        def expenseCategoryInstance = ExpenseCategory.get(params.id)
        if (!expenseCategoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expenseCategory.label', default: 'ExpenseCategory'), params.id])
            redirect(action: "list")
            return
        }

        [expenseCategoryInstance: expenseCategoryInstance]
    }

    def update() {
        def expenseCategoryInstance = ExpenseCategory.get(params.id)
        if (!expenseCategoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expenseCategory.label', default: 'ExpenseCategory'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (expenseCategoryInstance.version > version) {
                expenseCategoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'expenseCategory.label', default: 'ExpenseCategory')] as Object[],
                          "Another user has updated this ExpenseCategory while you were editing")
                render(view: "edit", model: [expenseCategoryInstance: expenseCategoryInstance])
                return
            }
        }

        expenseCategoryInstance.properties = params

        if (!expenseCategoryInstance.save(flush: true)) {
            render(view: "edit", model: [expenseCategoryInstance: expenseCategoryInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'expenseCategory.label', default: 'ExpenseCategory'), expenseCategoryInstance.id])
        redirect(action: "show", id: expenseCategoryInstance.id)
    }

    def delete() {
        def expenseCategoryInstance = ExpenseCategory.get(params.id)
        if (!expenseCategoryInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'expenseCategory.label', default: 'ExpenseCategory'), params.id])
            redirect(action: "list")
            return
        }

        try {
            expenseCategoryInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'expenseCategory.label', default: 'ExpenseCategory'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'expenseCategory.label', default: 'ExpenseCategory'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
