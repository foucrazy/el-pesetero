package es.elpesetero



import org.junit.*
import grails.test.mixin.*

@TestFor(ExpenseCategoryController)
@Mock(ExpenseCategory)
class ExpenseCategoryControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/expenseCategory/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.expenseCategoryInstanceList.size() == 0
        assert model.expenseCategoryInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.expenseCategoryInstance != null
    }

    void testSave() {
        controller.save()

        assert model.expenseCategoryInstance != null
        assert view == '/expenseCategory/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/expenseCategory/show/1'
        assert controller.flash.message != null
        assert ExpenseCategory.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/expenseCategory/list'


        populateValidParams(params)
        def expenseCategory = new ExpenseCategory(params)

        assert expenseCategory.save() != null

        params.id = expenseCategory.id

        def model = controller.show()

        assert model.expenseCategoryInstance == expenseCategory
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/expenseCategory/list'


        populateValidParams(params)
        def expenseCategory = new ExpenseCategory(params)

        assert expenseCategory.save() != null

        params.id = expenseCategory.id

        def model = controller.edit()

        assert model.expenseCategoryInstance == expenseCategory
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/expenseCategory/list'

        response.reset()


        populateValidParams(params)
        def expenseCategory = new ExpenseCategory(params)

        assert expenseCategory.save() != null

        // test invalid parameters in update
        params.id = expenseCategory.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/expenseCategory/edit"
        assert model.expenseCategoryInstance != null

        expenseCategory.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/expenseCategory/show/$expenseCategory.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        expenseCategory.clearErrors()

        populateValidParams(params)
        params.id = expenseCategory.id
        params.version = -1
        controller.update()

        assert view == "/expenseCategory/edit"
        assert model.expenseCategoryInstance != null
        assert model.expenseCategoryInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/expenseCategory/list'

        response.reset()

        populateValidParams(params)
        def expenseCategory = new ExpenseCategory(params)

        assert expenseCategory.save() != null
        assert ExpenseCategory.count() == 1

        params.id = expenseCategory.id

        controller.delete()

        assert ExpenseCategory.count() == 0
        assert ExpenseCategory.get(expenseCategory.id) == null
        assert response.redirectedUrl == '/expenseCategory/list'
    }
}
