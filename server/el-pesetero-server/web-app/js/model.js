Ember.Object.reopen({
	loadJSONArray: function(key,jsonArray,InnerObject) {		
		var currentArray = this.get(key)
		if (currentArray)
			currentArray.clear()
		else
			currentArray=Em.A()
		jsonArray.forEach(function(jsonObject){
			currentArray.pushObject(InnerObject.create(jsonObject))
		})	
		return currentArray
	}
})
var User = Ember.Object.extend({
	username: '',
	totalBalance: '',
	id: '',
	funds: Ember.A(),
	lastExpenses: Ember.A(),
	categories: Ember.A(),	
	loadJSONExpenses: function(jsonLastExpenses) {
		this.loadJSONArray('lastExpenses',jsonLastExpenses,Expense)
	},
	load: function() {
		var currentUser = this
		jQuery.getJSON('user/show/0.json', function(data) {
			currentUser.set('username',data.username)
			currentUser.set('totalBalance',data.totalBalance)
			currentUser.set('mail',data.mail)
			currentUser.loadJSONArray('lastExpenses',data.lastExpenses,Expense)			
			currentUser.loadJSONArray('funds',data.funds,Fund)
			currentUser.loadJSONArray('categories',data.topCategories,Ember.Object)
		})
	}
	
})
var Fund = Ember.Object.extend({
	showUrl: function() {
		return 'fund/show/'+this.get('id')
	},
	load: function(id) {
		console.log("El id pasado por parámetro: "+id)
		id = id || this.get('id')
		this.set('id',id)
		if (!id) {							
			alert("¡No puedo cargar un Fund sin Id!")		
			return null
		}
		var fund = this
		jQuery.getJSON('fund/show/'+id+'.json', function(data) {
			fund.set('quantity',data.quantity)
			fund.set('name',data.name)
			fund.set('type.name',data.fundType.name)
			fund.loadJSONArray('lastExpenses', data.lastExpenses, Expense)
		})
		return this
	},	
	id: null,
	name: null,
	quantity: 0.0,	
	type: Ember.Object.create(),
	lastExpenses: Em.A()
})
var Expense = Ember.Object.extend({
	_toJSON: function() {
		return {
				'category.id' : this.category,
				expenseDate : 'date.struct',
				expenseDate_day : this.day,
				expenseDate_month : this.month,
				expenseDate_year : this.year,
				'from.id' : this.from,
				name : this.name,
				quantity : this.quantity
		}
					
	},
	create: function(success, error) {		
		jQuery.post('expenseLine/save.json', this._toJSON(), function(data) {
			  if (data.success) {				  
				  success(data.expenseLine,data.success)
			  }
			  if (data.error && error) error(data.error)			  
		}, 'json');
	},
	clear: function() {
		this.set('name','')
		this.set('quantity',0)
		this.set('id',null)		
	},
	name: '',
	from: -1,
	category: '-1',
	quantity: '1.0',
	day: new Date().getDate(),
	month: new Date().getMonth(),
	year: new Date().getFullYear()
})
var MyDate = {
	monthNames: ['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre'],
	getMonths: function() {
    	var monthsArray = Em.A()
    	for (var i=0;i<this.monthNames.length;i++) {
    		monthsArray.pushObject(Ember.Object.create({
    			name: this.monthNames[i],
    			id: i
    		}))
    	}
    	return monthsArray
    },
	dayMonths: [31,28,31,30,31,30,31,31,30,31,30,31],
	numOfDaysOf: function(month,year) {
		if (month == 1 && this.isLeapYear(year)) {
			return 29
		} else {
			return this.dayMonths[month]
		}
	},
	daysArrayOf: function(month,year) {
		var numOfDays = this.numOfDaysOf(month,year)
		var daysArray = []
		for (var i = 1; i<=numOfDays;i++) {
			daysArray.pushObject(i)
		}
		return daysArray
	},
	years: [2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013],
	isLeapYear: function(year) {
        year = parseInt(year, 10);
        if(year % 4 == 0) {
            if(year % 100 != 0) {
                return true;
            } else {
                if(year % 400 == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}

var Withdrawal = Ember.Object.extend({
	create: function(success, error) {		
		jQuery.post('fund/withdrawCash.json', this._toJSON(), function(data) {
			  if (data.success) {				  
				  success(data.quantity,data.success)
			  }
			  if (data.error && error) error(data.error)			  
		}, 'json');
	},
	_toJSON: function() {
		var jsoned = {quantity : this.get('quantity')}
		if (this.get('from')!=null)
				jsoned.from = this.get('from')
		return jsoned
	},
	quantity: 0,
	from: null
})