modules = {
    application {
        resource url:'js/application.js'
    }
	index {
		dependsOn 'jquery-ui'
		resource url:'js/lib/handlebars-1.0.0.beta.6.js'
		resource url:'js/lib/ember-1.0.0-pre.2.js'
		resource url:'js/model.js'
		resource url:'js/indexApp.js'
	}
}