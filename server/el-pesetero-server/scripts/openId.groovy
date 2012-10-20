@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.2' )
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
	println resp.headers.location
	location = resp.headers.location
}

def googleHttp = new HTTPBuilder("https://www.google.com")
def locationIs = location.substring("https://www.google.com".size())
int indexOf = locationIs.indexOf("?")
def query = locationIs.substring(indexOf+1).tokenize('&').collectEntries{[it.tokenize('=')[0],it.tokenize('=')[1]]}
locationIs = locationIs.substring(0,indexOf)
println locationIs
println query
//http.setProxy 'localhost',3128, 'http'
googleHttp.request(Method.GET) {
	uri.path = locationIs
	uri.query = query
	response.success = { resp, html ->
		println html
	}
}