package es.elpesetero



import org.junit.*
import grails.test.mixin.*

@TestFor(LoginController)
@Mock(Login)
class LoginControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/login/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.loginInstanceList.size() == 0
        assert model.loginInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.loginInstance != null
    }

    void testSave() {
        controller.save()

        assert model.loginInstance != null
        assert view == '/login/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/login/show/1'
        assert controller.flash.message != null
        assert Login.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/login/list'


        populateValidParams(params)
        def login = new Login(params)

        assert login.save() != null

        params.id = login.id

        def model = controller.show()

        assert model.loginInstance == login
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/login/list'


        populateValidParams(params)
        def login = new Login(params)

        assert login.save() != null

        params.id = login.id

        def model = controller.edit()

        assert model.loginInstance == login
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/login/list'

        response.reset()


        populateValidParams(params)
        def login = new Login(params)

        assert login.save() != null

        // test invalid parameters in update
        params.id = login.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/login/edit"
        assert model.loginInstance != null

        login.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/login/show/$login.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        login.clearErrors()

        populateValidParams(params)
        params.id = login.id
        params.version = -1
        controller.update()

        assert view == "/login/edit"
        assert model.loginInstance != null
        assert model.loginInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/login/list'

        response.reset()

        populateValidParams(params)
        def login = new Login(params)

        assert login.save() != null
        assert Login.count() == 1

        params.id = login.id

        controller.delete()

        assert Login.count() == 0
        assert Login.get(login.id) == null
        assert response.redirectedUrl == '/login/list'
    }
}
