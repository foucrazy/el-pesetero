@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.2' )
import java.net.URLDecoder
import org.openid4java.util.HttpClientFactory
import org.openid4java.util.ProxyProperties
import es.elpesetero.FundType;
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*

def http = new HTTPBuilder("http://localhost:8080/el-pesetero-server")

def loginBody = [openid_identifier:'https://www.google.com/accounts/o8/id', _spring_security_remember_me: 'on']

//ProxyProperties proxyProps = new ProxyProperties();
//proxyProps.setProxyHostName("localhost");
//proxyProps.setProxyPort(3128);
//HttpClientFactory.setProxyProperties(proxyProps);

def location
http.post( path:'/el-pesetero-server/j_spring_openid_security_check', body: loginBody,  requestContentType: URLENC ) { resp ->
	println "Login response"
	println resp.statusLine.statusCode
	location = resp.headers.location
}

def googleHttp = new HTTPBuilder("https://www.google.com")
def locationIs = location.substring("https://www.google.com".size())
int indexOf = locationIs.indexOf("?")
def query = locationIs.substring(indexOf+1).tokenize('&').collectEntries{[it.tokenize('=')[0],URLDecoder.decode(it.tokenize('=')[1])]}
locationIs = locationIs.substring(0,indexOf)
println locationIs + query
//http.setProxy 'localhost',3128, 'http'
googleHttp.request(Method.GET) {
	uri.path = locationIs
	uri.query = query
	response.success = { resp, html ->
		println "Google response code $resp.statusLine.statusCode"
		//println "Form ${html.BODY.DIV.DIV.DIV.DIV.FORM}"
		println "--------------------"
		def action = html.BODY.DIV.DIV.DIV.DIV.FORM.@action.text()
		println "Action $action"
		html.BODY.DIV.DIV.DIV.DIV.FORM.INPUT.each { println it.@type.text() }
		println "--------------------"
		def hiddens = html.BODY.DIV.DIV.DIV.DIV.FORM.INPUT.list().collectEntries { [(it.@name.text()): it.@value.text()]}
		hiddens << [Email: 'kortatu@gmail.com']
		hiddens << [Passwd: 'runn1ng0ut']
		println hiddens 
		println "--------------------"
		def accountsHttp = new HTTPBuilder("https://accounts.google.com")
		accountsHttp.post (path: '/ServiceLoginAuth', body: hiddens,  requestContentType: URLENC ) { respGoogle, loginHtml ->
			println "Google Login response"
			println respGoogle.statusLine.statusCode
			location = respGoogle.headers.location
			println loginHtml
		}
	}
}