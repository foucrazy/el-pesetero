class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"(parseRequest: true){
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
