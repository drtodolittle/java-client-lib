package de.drtodolittle.client

import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*


public class DrToDoLittleClient {

	def drtodolittle = new RESTClient( 'http://www.drtodolittle.de' )
	def token
	
	public void login(def username, def password) {
		def response = drtodolittle.post( path : '/api/todos/login',
			contentType: JSON,
			requestContentType:  JSON,
			body: [email: username, password: password])
		if (response.status == 200) {
			token = response.data.token
		}
	}

	public void deleteToDo(String id) {
		def response = drtodolittle.delete( path : "/api/todos/$id",
			headers: [authorization: "bearer " + token])
		if (response.status == 204) {
		}
	}

	public void done(String id) {
		def response = drtodolittle.get( path : "/api/todos/$id/done",
			headers: [authorization: "bearer " + token])
		if (response.status == 204) {
		}
	}

}