package com.sample.model

data class TodoResponse (val id: Int, val name: String, val completed: Boolean)

fun responseMapper(todo: Todo) = TodoResponse(todo.id, todo.name, todo.completed)