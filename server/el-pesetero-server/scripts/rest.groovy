import es.elpesetero.FundType;
@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.2' )
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*

def http = new HTTPBuilder("http://localhost:8080/el-pesetero-server")

def login(http) {
	println "Creating Funds"
	def loginBody = [j_username:'kortatu',j_password:'password', _spring_security_remember_me: 'on']
	
	//http.request(Method.GET, JSON) {
	//	uri.path = '/el-pesetero-server/login/authP'
	//	response.success = { resp, json ->
	//		println resp.statusLine
	//		json.each  {
	//		  println it.webName
	//		}
	//
	//	}
	//}
	http.post( path:'/el-pesetero-server/j_spring_security_check', body: loginBody,  requestContentType: URLENC ) { resp ->
		println "Login response"
		println resp.statusLine.statusCode
		println resp.headers.location
	}
	
	http.request(Method.GET, JSON) {
		uri.path = '/el-pesetero-server/fund/list.json'
		response.success = { resp, json ->
			println json
		}
	}
}

def createFunds(http) {
	
	
	def nomina
	http.request(Method.POST, JSON) {
		body = [name: 'Nómina', quantity:6040.34, type: FundType.bankAccount]
		uri.path = '/el-pesetero-server/fund/save.json'
		response.success = { resp, json ->
			println json
			nomina = json.fund
		}
	}
	
	def compartida
	http.request(Method.POST, JSON) {
		body = [name: 'Compartida', quantity:6040.34, type: FundType.bankAccount]
		uri.path = '/el-pesetero-server/fund/save.json'
		response.success = { resp, json ->
			println json
			compartida = json.fund
		}
	}
}

def withdrawFrom(http,from) {
	println "Sacando dinero de $from.name"
	http.request(Method.POST, JSON) {
		body = [quantity:2000, from: from.id]
		uri.path = '/el-pesetero-server/fund/withdrawCash.json'
		response.success = { resp, json ->
			println json
			println "Cantidad sacada $json.quantity de $from.name"
		}
	}
}

def withdraw(http) {
	println "Withdrawing money"
	http.request(Method.POST, JSON) {
		body = [quantity:200]
		uri.path = '/el-pesetero-server/fund/withdrawCash.json'
		response.success = { resp, json ->
			println json
			println "Cantidad sacada $json.quantity"
		}
	}
}
	
def listFunds(http) {	
	http.request(Method.GET, JSON) {
		uri.path = '/el-pesetero-server/fund/list.json'
		response.success = { resp, json ->
			json
		}
	}
}

def addExpenses(http, quantity, name) {
	println "Creating an expense"
	
	http.request(Method.POST, JSON) {
		body = [expenseDate:"date.struct",expenseDate_day:'19',expenseDate_month:'10',expenseDate_year:'2012', name:name, 'category.id':3, 'from.id':1, quantity:quantity,create:"Create"]
		uri.path = '/el-pesetero-server/expenseLine/save.json'
		response.success = { resp, json ->
			println json
			println resp.statusLine.statusCode
			println resp.headers.location?"Me redirige a $resp.headers.location":""
		}
	}
}

login(http)
//createFunds(http)
//withdraw(http)
def funds = listFunds(http)
println "Funds : ${funds}"
def cash = funds.find {it.name =='Efectivo'}
if (cash.quantity<2000) {
	def comun = funds.find {it.name=='Compartida'}
	println "Intento sacar 2000 de la común porque no tengo sufciente efectivo para la bici"
	println comun
	withdrawFrom(http, comun)
} else
	println "Tengo suficiente efectivo, no saco nada"
println "Me compro la bici"
addExpenses(http,2000,'La bici')
