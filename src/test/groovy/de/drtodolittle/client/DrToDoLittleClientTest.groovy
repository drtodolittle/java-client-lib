package de.drtodolittle.client

import spock.lang.Specification
import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*

class DrToDoLittleClientTest extends Specification {
	
	def "Get ToDo-List"() {
		
		given: "A client with an user who is logged on"
			def client = new DrToDoLittleClient()
			client.login("dirk", "dirk1234")
		
		when: "request all todos"
			def todos = client.getToDos()
		then: "in minimun one todo will return"
			todos.size() > 0
	}
	
	def "Create new ToDo"() {
		
		given: "A client with an user who is logged on"
			def client = new DrToDoLittleClient()
			client.login("dirk", "dirk1234")
		
		when: "add a new topic"
			def todo = client.createToDo("Neues ToDo")
		then: "a todo is created with the same topic"
			todo.topic.equals("Neues ToDo")
	}
	
	def "Delete a ToDo"() {
		
		given: "A client with an user who is logged on and a todo"
			def client = new DrToDoLittleClient()
			client.login("dirk", "dirk1234")
			def todo = client.createToDo("Schönes Topic zum Löschen")
		
		when: "delete a todo"
			client.deleteToDo(todo.id)
		then: "the todo is not in the todolist"
			def todos = client.getToDos()
			def notexist = true
			todos.each({
				if (todo.id.equals(it.id)) {
					notexist = false
				}
			})
			notexist
	} 


	def "Set a ToDo on done"() {
		
		given: "A client with an user who is logged on and a todo"
			def client = new DrToDoLittleClient()
			client.login("dirk", "dirk1234")
			def todo = client.createToDo("Schönes Topic zum Erledigen")
		
		when: "set to done"
			client.done(todo.id)
		then: "the todo is in the todolist and done is true"
			def todos = client.getToDos()
			def isdone = false
			todos.each({
				if (todo.id.equals(it.id)) {
					isdone=it.done
				}
			})
			isdone
	} 
	
	def "Update topic"() {
		given: "A client with an user who is logged on and a todo"
			def client = new DrToDoLittleClient()
			client.login("dirk", "dirk1234")
			def todo = client.createToDo("Schönes Topic zum Ändern")
		
		when: "update the topic to Schönes Topic geändert"
			client.updateTopic(todo.id, "Schönes Topic geändert")
		then: "the todo has the topic "Schönes Topic geändert"
			def todos = client.getToDos()
			def targetToDo
			todos.each({
				if (todo.id.equals(it.id)) {
					targetToDo=it
				}
			})
			targetToDo.topic.equals("Schönes Topic geändert")
	}
}