package com.sample.controller

import com.sample.model.Todo
import com.sample.repository.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController {
    @Autowired
    lateinit var repository: TodoRepository

    @GetMapping("/todos")
    fun findAll(): List<Todo> = repository.findAll()
}
