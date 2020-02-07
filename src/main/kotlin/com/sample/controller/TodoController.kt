package com.sample.controller

import com.sample.model.Todo
import com.sample.repository.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = ["http://localhost:4523"])
@RestController
@RequestMapping("/todos")
class TodoController {
    @Autowired
    lateinit var repository: TodoRepository

    @GetMapping
    fun findAll(): List<TodoResponse> = repository.findAll().map{ todo -> responseMapper(todo) }

    @PostMapping
    fun add(@RequestBody todo: Todo): TodoResponse = responseMapper(repository.save(todo))

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): Optional<TodoResponse> = repository.findById(id).map { todo -> responseMapper(todo) }
}
