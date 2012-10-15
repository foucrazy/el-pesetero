package es.elpesetero.security



class OpenID {

	String url

	static belongsTo = [user: SecurityUser]

	static constraints = {
		url unique: true
	}
}
