package es.elpesetero

import grails.test.mixin.TestFor
import grails.test.mixin.Mock

@TestFor(ExpenseController)
@Mock(Expense)
class ExpenseControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/expense/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.expenseInstanceList.size() == 0
        assert model.expenseInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.expenseInstance != null
    }

    void testSave() {
        controller.save()

        assert model.expenseInstance != null
        assert view == '/expense/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/expense/show/1'
        assert controller.flash.message != null
        assert Expense.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/expense/list'


        populateValidParams(params)
        def expense = new Expense(params)

        assert expense.save() != null

        params.id = expense.id

        def model = controller.show()

        assert model.expenseInstance == expense
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/expense/list'


        populateValidParams(params)
        def expense = new Expense(params)

        assert expense.save() != null

        params.id = expense.id

        def model = controller.edit()

        assert model.expenseInstance == expense
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/expense/list'

        response.reset()


        populateValidParams(params)
        def expense = new Expense(params)

        assert expense.save() != null

        // test invalid parameters in update
        params.id = expense.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/expense/edit"
        assert model.expenseInstance != null

        expense.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/expense/show/$expense.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        expense.clearErrors()

        populateValidParams(params)
        params.id = expense.id
        params.version = -1
        controller.update()

        assert view == "/expense/edit"
        assert model.expenseInstance != null
        assert model.expenseInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/expense/list'

        response.reset()

        populateValidParams(params)
        def expense = new Expense(params)

        assert expense.save() != null
        assert Expense.count() == 1

        params.id = expense.id

        controller.delete()

        assert Expense.count() == 0
        assert Expense.get(expense.id) == null
        assert response.redirectedUrl == '/expense/list'
    }
}
