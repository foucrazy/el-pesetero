package es.elpesetero.security



import org.junit.*
import grails.test.mixin.*

@TestFor(SecurityUserController)
@Mock(SecurityUser)
class SecurityUserControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/securityUser/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.securityUserInstanceList.size() == 0
        assert model.securityUserInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.securityUserInstance != null
    }

    void testSave() {
        controller.save()

        assert model.securityUserInstance != null
        assert view == '/securityUser/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/securityUser/show/1'
        assert controller.flash.message != null
        assert SecurityUser.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/securityUser/list'


        populateValidParams(params)
        def securityUser = new SecurityUser(params)

        assert securityUser.save() != null

        params.id = securityUser.id

        def model = controller.show()

        assert model.securityUserInstance == securityUser
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/securityUser/list'


        populateValidParams(params)
        def securityUser = new SecurityUser(params)

        assert securityUser.save() != null

        params.id = securityUser.id

        def model = controller.edit()

        assert model.securityUserInstance == securityUser
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/securityUser/list'

        response.reset()


        populateValidParams(params)
        def securityUser = new SecurityUser(params)

        assert securityUser.save() != null

        // test invalid parameters in update
        params.id = securityUser.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/securityUser/edit"
        assert model.securityUserInstance != null

        securityUser.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/securityUser/show/$securityUser.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        securityUser.clearErrors()

        populateValidParams(params)
        params.id = securityUser.id
        params.version = -1
        controller.update()

        assert view == "/securityUser/edit"
        assert model.securityUserInstance != null
        assert model.securityUserInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/securityUser/list'

        response.reset()

        populateValidParams(params)
        def securityUser = new SecurityUser(params)

        assert securityUser.save() != null
        assert SecurityUser.count() == 1

        params.id = securityUser.id

        controller.delete()

        assert SecurityUser.count() == 0
        assert SecurityUser.get(securityUser.id) == null
        assert response.redirectedUrl == '/securityUser/list'
    }
}
