package com.sample.repository

import com.sample.model.Todo
import org.springframework.stereotype.Repository

@Repository
class TodoRepository {
    val todos = mutableListOf(
            Todo(id = 1, name = "drink water", completed = false),
            Todo(id = 2, name = "feed the cat", completed = false),
            Todo(id = 3, name = "buy food", completed = false)
    )

    fun findAll(): MutableList<Todo> {
        return todos
    }
}