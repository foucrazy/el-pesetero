package es.elpesetero

import org.springframework.dao.DataIntegrityViolationException

import es.elpesetero.security.SecurityUser
import grails.converters.JSON
import grails.converters.XML
import grails.plugins.springsecurity.Secured

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils;


class UserController extends BaseAuthController{
	
	def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
		def user = authenticatedUser()
		if (!user) {
			redirect(action:"auth", controller:"openId")
		} else {
			withFormat {
				html {
					render(view: "/index", model: [userInstance: user])
				}
				json {
					render userInstance as JSON
				}
				xml {
					render userInstance as XML
				}
			}
		}        
    }

	@Secured(['ROLE_ADMIN'])
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

	@Secured(['ROLE_ADMIN'])
    def create() {
        [userInstance: new User(params)]
    }

	@Secured(['ROLE_ADMIN'])
    def save() {
        def userInstance = new User(params)
        if (!userInstance.save(flush: true)) {
            render(view: "create", model: [userInstance: userInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

	@Secured(['ROLE_USER'])
    def show() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
        	def flashMessage = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
			withFormat {
				html {
					flash.message = flashMessage
					redirect(action: "list")
				}
				json {
					def jsonError = [flashMessage]
					render jsonError as JSON
				}
			}
            return
        }
		
		if (userInstance == authenticatedUser() || isAdmin()) {
			withFormat {
				html {
					[userInstance: userInstance]
				}
				json {
					render userInstance as JSON
				}
				xml {
					render userInstance as XML
				}
			}
		} else redirect(controller:'login', action: 'denied')
        
    }

	private authenticatedUser() {
		def securityUserName = springSecurityService.authentication.name
		if (securityUserName) {
			SecurityUser securityUser = SecurityUser.findByUsername(securityUserName)
			if (securityUser)
				User.findBySecurityUser(securityUser)
		}
	}

	private isAdmin() {
		def isAdmin = SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')
	}

	@Secured(['ROLE_ADMIN'])
    def edit() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

	@Secured(['ROLE_ADMIN'])
    def update() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'user.label', default: 'User')] as Object[],
                          "Another user has updated this User while you were editing")
                render(view: "edit", model: [userInstance: userInstance])
                return
            }
        }

        userInstance.properties = params

        if (!userInstance.save(flush: true)) {
            render(view: "edit", model: [userInstance: userInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

	@Secured(['ROLE_ADMIN'])
    def delete() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        try {
			SecurityUser securityUser = userInstance.securityUser
            userInstance.delete(flush: true)
			securityUser.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
        } catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), userInstance.username])
            redirect(action: "show", id: params.id)
        }
    }
}
