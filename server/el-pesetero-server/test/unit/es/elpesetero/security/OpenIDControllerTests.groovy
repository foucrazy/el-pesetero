package es.elpesetero.security



import org.junit.*
import grails.test.mixin.*

@TestFor(OpenIDController)
@Mock(OpenID)
class OpenIDControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/openID/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.openIDInstanceList.size() == 0
        assert model.openIDInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.openIDInstance != null
    }

    void testSave() {
        controller.save()

        assert model.openIDInstance != null
        assert view == '/openID/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/openID/show/1'
        assert controller.flash.message != null
        assert OpenID.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/openID/list'


        populateValidParams(params)
        def openID = new OpenID(params)

        assert openID.save() != null

        params.id = openID.id

        def model = controller.show()

        assert model.openIDInstance == openID
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/openID/list'


        populateValidParams(params)
        def openID = new OpenID(params)

        assert openID.save() != null

        params.id = openID.id

        def model = controller.edit()

        assert model.openIDInstance == openID
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/openID/list'

        response.reset()


        populateValidParams(params)
        def openID = new OpenID(params)

        assert openID.save() != null

        // test invalid parameters in update
        params.id = openID.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/openID/edit"
        assert model.openIDInstance != null

        openID.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/openID/show/$openID.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        openID.clearErrors()

        populateValidParams(params)
        params.id = openID.id
        params.version = -1
        controller.update()

        assert view == "/openID/edit"
        assert model.openIDInstance != null
        assert model.openIDInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/openID/list'

        response.reset()

        populateValidParams(params)
        def openID = new OpenID(params)

        assert openID.save() != null
        assert OpenID.count() == 1

        params.id = openID.id

        controller.delete()

        assert OpenID.count() == 0
        assert OpenID.get(openID.id) == null
        assert response.redirectedUrl == '/openID/list'
    }
}
