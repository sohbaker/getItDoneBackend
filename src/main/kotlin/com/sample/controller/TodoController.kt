package com.sample.controller

import com.sample.model.Todo
import com.sample.repository.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestBody

@CrossOrigin(origins = ["http://localhost:4523"])
@RestController
@RequestMapping("/todos")
class TodoController {
    @Autowired
    lateinit var repository: TodoRepository

    @GetMapping
    fun findAll(): Iterable<Todo> = repository.findAll()

    @PostMapping
    fun add(@RequestBody todo: Todo): Todo = repository.save(todo)
}
