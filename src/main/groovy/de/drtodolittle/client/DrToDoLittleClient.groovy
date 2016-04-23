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

	public ToDo createToDo(def topic) {
		def response = drtodolittle.post( path : '/api/todos',
			contentType: JSON,
			requestContentType:  JSON,
			headers: [authorization: "bearer " + token],
			body: [topic: topic, done: false])
		if (response.status == 200) {
			new ToDo(id: response.data.id, topic: topic, done: false)
		}
	
	}

	public ToDo[] getToDos() {
		def response = drtodolittle.get( path : '/api/todos',
			contentType: JSON,
			requestContentType:  JSON,
			headers: [authorization: "bearer " + token])
		if (response.status == 200) {
			response.data
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