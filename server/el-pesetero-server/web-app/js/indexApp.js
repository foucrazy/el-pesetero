var IndexApp = Ember.Application.create({
	rootElement: '#appHolder',
	currentUser: User.create(),
	lastMessage: null,
	lastError: null,
	clearMessages: function() {
		this.set('lastMessage',null)
		this.set('lastError',null)
	},
	error: function(error){
		IndexApp.set('lastMessage',null)
		IndexApp.set('lastError',error)			    			
	},
	Router : Ember.Router.extend({
		enableLogging: true,
		goToMe: Ember.Route.transitionTo('root.me'),
		goToExpenses: Ember.Route.transitionTo('root.expenses'),
		goToExpense: Ember.Route.transitionTo('root.expense'),
		goToFund: Ember.Route.transitionTo('root.fund'),
		addExpense: Ember.Route.transitionTo('root.expenses.add'),
		withdraw: Ember.Route.transitionTo('root.funds.withdraw'),
		root: Ember.Route.extend({
		    index: Ember.Route.extend({
		      route: '/',
		      enter: function(router) {
		    	  console.log("Entering Index.")
//		    	  var user = router.get('meController').get('content')
//		    	  router.get('applicationController').set('content', user);
		    	  router.transitionTo('me')
		      }
		    }),
		    me: Ember.Route.extend({
		    	route: '/me',
		    	enter: function(router) {
		    		IndexApp.clearMessages()		    		
		    		console.log("Entering Me.")
		    	},
		    	connectOutlets: function(router, context){	    		  	    		
		    		router.get('applicationController').connectOutlet('me')
			    }
		    	
		    }),
		    funds: Ember.Route.extend({
		    	route: '/funds',
		    	connectOutlets: function(router, context){
		    		router.get('applicationController').connectOutlet('funds',context)
//		    		router.get('expensesController').disconnectOutlet('expenseEdit')
		    	},
		    	withdraw: Ember.Route.extend({
		    		route: '/withdraw',
		    		connectOutlets: function(router, context){
			    		router.get('fundsController').connectOutlet('withdraw')
			    	},
			    	submitWithdraw: function(event) {
			    		var withdrawal = event.withdrawController.get('content')
			    		var success = function(quantity,message) {
			    			IndexApp.set('lastMessage',message)
			    			IndexApp.set('error',null)
			    			var currentUser = IndexApp.get('currentUser')
			    			currentUser.load()
			    		}
			    		withdrawal.create(success,IndexApp.error)
			    	}
		    	})		    	
		    }),
		    fund: Ember.Route.extend({
		    	route: '/fund/:id',
		    	enter: function(router) {		    		
		    		console.log("Entrando en router Fund: "+router.get('fundController').get('content').get('id'))		    		
		    	},
		    	connectOutlets: function(router, context){
		    		router.get('applicationController').connectOutlet('fund',context)
		    		var fundController = router.get('fundController')
		    		var content = fundController.get('content')
		    		console.log('Connecting outlet en Fund '+content)
		    		if (!content.load) {
		    			console.log("Convierto en Ember.FUnd el objeto")
		    			content = Fund.create(content)
		    			fundController.set('content',content)
		    		}
		    		content.load()
		    	},
		    	serialize:  function(router, context){
	    			  return { id: context.id }
		    	},
		    	deserialize:  function(router, context){		    		
		    		return Fund.create().load(context.id)
		    	}
		    }),
		    expenses: Ember.Route.extend({
		    	route: '/expenses',
		    	connectOutlets: function(router, context){
		    		router.get('applicationController').connectOutlet('expenses',context)
		    		router.get('expensesController').disconnectOutlet('expenseEdit')
		    	},
		    	add: Ember.Route.extend({
		    		route: '/add',
		    		connectOutlets: function(router, context){
			    		router.get('expensesController').connectOutlet('expenseEdit','expenseAdd')
			    	},
			    	submitExpense: function(event) {
			    		var expenseLine = event.expenseAddController.get('content')
			    		expenseLine.create(function(newExpense,message){
			    			var currentUser = IndexApp.get('currentUser')
			    			currentUser.load()
			    			IndexApp.set('lastMessage',message)
			    			IndexApp.set('error',null)
			    			expenseLine.clear()			    			
			    		},IndexApp.error)
			    			    					    	
			    	}
		    	}),
		    	expense: Ember.Route.extend({
		    		route: "/expense"
		    	})
		    })
		})
	}),
	ApplicationController: Ember.Controller.extend({
		currentUserBinding: 'IndexApp.currentUser'
	}),
	ApplicationView: Ember.View.extend({
		templateName: 'application'
	}),
	MeView: Ember.View.extend({
		templateName: 'me'
	}),
	MeController: Ember.Controller.extend({
		meBinding: 'IndexApp.currentUser'
	}),
	ExpensesView: Ember.View.extend({
		templateName: 'expenses'
	}),
	ExpensesController: Ember.ArrayController.extend({
		meBinding: 'IndexApp.currentUser',
		content: Em.A()
	}),
	ExpenseView: Ember.View.extend({
		templateName: 'expense'		
	}),
	ExpenseController: Ember.Controller.extend({
		content: {name: 'empty'}		
	}),
	ExpenseAddView: Ember.View.extend({		
		templateName: 'expenseAdd',
		classNames: ['ui-widget']
	}),
	ExpenseAddController: Ember.Controller.extend({
		content: Expense.create(),
		days: function() {
			return MyDate.daysArrayOf(this.get('content.month'),this.get('content.year'))			
		}.property('content.month','content.year'),
		months: MyDate.getMonths(),
		years: MyDate.years,
		categories: function() {
			var arrai = Em.A()
			arrai.pushObject({name:'',id:''})
			arrai.pushObjects(IndexApp.get('currentUser.categories'))
			return arrai
		}.property('IndexApp.currentUser.categories'),
		fundsBinding: 'IndexApp.currentUser.funds'
		
	}),
	FundsView: Ember.View.extend({
		templateName: 'funds'
	}),
	FundsController: Ember.ArrayController.extend({
		meBinding: 'IndexApp.currentUser',
		content: Em.A(),
	}),
	FundView: Ember.View.extend({
		templateName: 'fund'
	}),
	FundController: Ember.Controller.extend({
		content: Fund.create(),
		fundElementId: function() {
			return 'fundData-'+this.get('content.id')
		}.property('content.id')
	}),
	WithdrawView: Ember.View.extend({
		templateName: 'withdraw',
		classNames: ['ui-widget']
	}),
	WithdrawController: Ember.Controller.extend({
		content: Withdrawal.create(),
		funds: function() {
			return IndexApp.get('currentUser.funds').filter(function(fund) {
				return fund.type.name == 'bankAccount'
			})
		}.property('IndexApp.currentUser.funds'),
		severalAccounts: function() {
			return this.get('funds').length>1
		}.property('funds')
	}),
	isMain: function() {
		var currentState = this.get('router.currentState.name')
		return currentState=='me' || currentState == 'index' 
	}.property("router.currentState.name")
})
IndexApp.ready = function() {
	var currentUser = this.get('currentUser')
	currentUser.load()
}

IndexApp.initialize()
