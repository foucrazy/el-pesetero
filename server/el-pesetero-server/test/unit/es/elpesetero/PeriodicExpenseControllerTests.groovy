package es.elpesetero

import grails.test.mixin.TestFor
import grails.test.mixin.Mock

@TestFor(PeriodicExpenseController)
@Mock(PeriodicExpense)
class PeriodicExpenseControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/periodicExpense/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.periodicExpenseInstanceList.size() == 0
        assert model.periodicExpenseInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.periodicExpenseInstance != null
    }

    void testSave() {
        controller.save()

        assert model.periodicExpenseInstance != null
        assert view == '/periodicExpense/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/periodicExpense/show/1'
        assert controller.flash.message != null
        assert PeriodicExpense.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/periodicExpense/list'


        populateValidParams(params)
        def periodicExpense = new PeriodicExpense(params)

        assert periodicExpense.save() != null

        params.id = periodicExpense.id

        def model = controller.show()

        assert model.periodicExpenseInstance == periodicExpense
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/periodicExpense/list'


        populateValidParams(params)
        def periodicExpense = new PeriodicExpense(params)

        assert periodicExpense.save() != null

        params.id = periodicExpense.id

        def model = controller.edit()

        assert model.periodicExpenseInstance == periodicExpense
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/periodicExpense/list'

        response.reset()


        populateValidParams(params)
        def periodicExpense = new PeriodicExpense(params)

        assert periodicExpense.save() != null

        // test invalid parameters in update
        params.id = periodicExpense.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/periodicExpense/edit"
        assert model.periodicExpenseInstance != null

        periodicExpense.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/periodicExpense/show/$periodicExpense.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        periodicExpense.clearErrors()

        populateValidParams(params)
        params.id = periodicExpense.id
        params.version = -1
        controller.update()

        assert view == "/periodicExpense/edit"
        assert model.periodicExpenseInstance != null
        assert model.periodicExpenseInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/periodicExpense/list'

        response.reset()

        populateValidParams(params)
        def periodicExpense = new PeriodicExpense(params)

        assert periodicExpense.save() != null
        assert PeriodicExpense.count() == 1

        params.id = periodicExpense.id

        controller.delete()

        assert PeriodicExpense.count() == 0
        assert PeriodicExpense.get(periodicExpense.id) == null
        assert response.redirectedUrl == '/periodicExpense/list'
    }
}
