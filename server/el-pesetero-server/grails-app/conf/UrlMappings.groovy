class UrlMappings {

	static mappings = {
		
		"/login/authP" {
			controller = 'login'
			action = 'auth'
		 }
		"/login/auth" {
			controller = 'openId'
			action = 'auth'
		 }
		 "/login/openIdCreateAccount" {
			controller = 'openId'
			action = 'createAccount'
		 }
		 
		"/$controller/$action?/$id?"(parseRequest: true){
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
