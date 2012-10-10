package es.elpesetero



import org.junit.*
import grails.test.mixin.*

@TestFor(ExpenseLineController)
@Mock(ExpenseLine)
class ExpenseLineControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/expenseLine/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.expenseLineInstanceList.size() == 0
        assert model.expenseLineInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.expenseLineInstance != null
    }

    void testSave() {
        controller.save()

        assert model.expenseLineInstance != null
        assert view == '/expenseLine/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/expenseLine/show/1'
        assert controller.flash.message != null
        assert ExpenseLine.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/expenseLine/list'


        populateValidParams(params)
        def expenseLine = new ExpenseLine(params)

        assert expenseLine.save() != null

        params.id = expenseLine.id

        def model = controller.show()

        assert model.expenseLineInstance == expenseLine
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/expenseLine/list'


        populateValidParams(params)
        def expenseLine = new ExpenseLine(params)

        assert expenseLine.save() != null

        params.id = expenseLine.id

        def model = controller.edit()

        assert model.expenseLineInstance == expenseLine
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/expenseLine/list'

        response.reset()


        populateValidParams(params)
        def expenseLine = new ExpenseLine(params)

        assert expenseLine.save() != null

        // test invalid parameters in update
        params.id = expenseLine.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/expenseLine/edit"
        assert model.expenseLineInstance != null

        expenseLine.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/expenseLine/show/$expenseLine.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        expenseLine.clearErrors()

        populateValidParams(params)
        params.id = expenseLine.id
        params.version = -1
        controller.update()

        assert view == "/expenseLine/edit"
        assert model.expenseLineInstance != null
        assert model.expenseLineInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/expenseLine/list'

        response.reset()

        populateValidParams(params)
        def expenseLine = new ExpenseLine(params)

        assert expenseLine.save() != null
        assert ExpenseLine.count() == 1

        params.id = expenseLine.id

        controller.delete()

        assert ExpenseLine.count() == 0
        assert ExpenseLine.get(expenseLine.id) == null
        assert response.redirectedUrl == '/expenseLine/list'
    }
}
