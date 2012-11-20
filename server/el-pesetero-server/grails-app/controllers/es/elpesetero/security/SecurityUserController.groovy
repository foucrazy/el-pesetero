package es.elpesetero.security

import grails.plugins.springsecurity.Secured;

import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_ADMIN'])
class SecurityUserController {
	
	def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [securityUserInstanceList: SecurityUser.list(params), securityUserInstanceTotal: SecurityUser.count()]
    }

    def create() {
        [securityUserInstance: new SecurityUser(params)]
    }

    def save() {
        def securityUserInstance = new SecurityUser(params)
        if (!securityUserInstance.save(flush: true)) {
            render(view: "create", model: [securityUserInstance: securityUserInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'securityUser.label', default: 'SecurityUser'), securityUserInstance.id])
        redirect(action: "show", id: securityUserInstance.id)
    }
	
	@Secured(['ROLE_USER'])
    def show() {
        def securityUserInstance = SecurityUser.get(params.id)
        if (!securityUserInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'securityUser.label', default: 'SecurityUser'), params.id])
            redirect(action: "list")
            return
        }		

        [securityUserInstance: securityUserInstance]
    }

	@Secured(['ROLE_USER'])
    def edit() {
        def securityUserInstance = SecurityUser.get(params.id)
        if (!securityUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'securityUser.label', default: 'SecurityUser'), params.id])
            redirect(action: "list")
            return
        }

        [securityUserInstance: securityUserInstance]
    }

	@Secured(['ROLE_USER'])
    def update() {
        def securityUserInstance = SecurityUser.get(params.id)
        if (!securityUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'securityUser.label', default: 'SecurityUser'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (securityUserInstance.version > version) {
                securityUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'securityUser.label', default: 'SecurityUser')] as Object[],
                          "Another user has updated this SecurityUser while you were editing")
                render(view: "edit", model: [securityUserInstance: securityUserInstance])
                return
            }
        }

        securityUserInstance.properties = params
		def newPassword = params.newPassword
		println "New Password $newPassword"
		if (newPassword) {
			def newPasswordConfirm = params.newPasswordConfirm
			println "New PasswordConfirm $newPasswordConfirm"
			if (newPassword==newPasswordConfirm) {
				securityUserInstance.password = newPassword
			} else {
				securityUserInstance.errors.rejectValue("newPasswordConfirm", "notCoincident",
				[message(code: 'securityUser.label', default: 'SecurityUser')] as Object[],
				"Las password no coinciden")
				render(view: "edit", model: [securityUserInstance: securityUserInstance])
				return
			}
				
		}
        if (!securityUserInstance.save(flush: true)) {
            render(view: "edit", model: [securityUserInstance: securityUserInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'securityUser.label', default: 'SecurityUser'), securityUserInstance.id])
        redirect(action: "show", id: securityUserInstance.id)
    }

    def delete() {
        def securityUserInstance = SecurityUser.get(params.id)
        if (!securityUserInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'securityUser.label', default: 'SecurityUser'), params.id])
            redirect(action: "list")
            return
        }

        try {
            securityUserInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'securityUser.label', default: 'SecurityUser'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'securityUser.label', default: 'SecurityUser'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
