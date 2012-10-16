package es.elpesetero.security

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured;


class OpenIDController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

	@Secured(['ROLE_ADMIN'])
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [openIDInstanceList: OpenID.list(params), openIDInstanceTotal: OpenID.count()]
    }

	@Secured(['ROLE_ADMIN'])
    def create() {
        [openIDInstance: new OpenID(params)]
    }

	@Secured(['ROLE_ADMIN'])
    def save() {
        def openIDInstance = new OpenID(params)
        if (!openIDInstance.save(flush: true)) {
            render(view: "create", model: [openIDInstance: openIDInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'openID.label', default: 'OpenID'), openIDInstance.id])
        redirect(action: "show", id: openIDInstance.id)
    }

	@Secured(['ROLE_ADMIN'])
    def show() {
        def openIDInstance = OpenID.get(params.id)
        if (!openIDInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'openID.label', default: 'OpenID'), params.id])
            redirect(action: "list")
            return
        }

        [openIDInstance: openIDInstance]
    }

	@Secured(['ROLE_ADMIN'])
    def edit() {
        def openIDInstance = OpenID.get(params.id)
        if (!openIDInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'openID.label', default: 'OpenID'), params.id])
            redirect(action: "list")
            return
        }

        [openIDInstance: openIDInstance]
    }

	@Secured(['ROLE_ADMIN'])
    def update() {
        def openIDInstance = OpenID.get(params.id)
        if (!openIDInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'openID.label', default: 'OpenID'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (openIDInstance.version > version) {
                openIDInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'openID.label', default: 'OpenID')] as Object[],
                          "Another user has updated this OpenID while you were editing")
                render(view: "edit", model: [openIDInstance: openIDInstance])
                return
            }
        }

        openIDInstance.properties = params

        if (!openIDInstance.save(flush: true)) {
            render(view: "edit", model: [openIDInstance: openIDInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'openID.label', default: 'OpenID'), openIDInstance.id])
        redirect(action: "show", id: openIDInstance.id)
    }

	@Secured(['ROLE_ADMIN'])
    def delete() {
        def openIDInstance = OpenID.get(params.id)
        if (!openIDInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'openID.label', default: 'OpenID'), params.id])
            redirect(action: "list")
            return
        }

        try {
            openIDInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'openID.label', default: 'OpenID'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'openID.label', default: 'OpenID'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
