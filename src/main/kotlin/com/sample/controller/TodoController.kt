package com.sample.controller

import com.sample.model.Todo
import com.sample.repository.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin(origin = "http://localhost:4523")
@RestController
class TodoController {
    @Autowired
    lateinit var repository: TodoRepository

    @GetMapping("/todos")
    fun findAll(): List<Todo> = repository.findAll()
}
