package com.sample.model

data class TodoRequest(val id: Int, val name: String, val completed: Boolean)

fun requestMapper(todoRequest: TodoRequest) = Todo(todoRequest.id, todoRequest.name, todoRequest.completed)