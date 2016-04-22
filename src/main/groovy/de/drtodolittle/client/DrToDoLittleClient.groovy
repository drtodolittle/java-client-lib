package de.drtodolittle.client

import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*


public class DrToDoLittleClient {

	def drtodolittle = new RestClient()


	public void deleteToDo(String id) {
		def response = drtodolittle.delete( path : "/api/todos/$id",
			headers: [authorization: "bearer " + token])
		if (response.status == 204) {
		}
	}
}