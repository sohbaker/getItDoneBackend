package com.sample.controller

import com.sample.model.TodoRequest
import com.sample.model.TodoResponse
import com.sample.model.requestMapper
import com.sample.model.responseMapper
import com.sample.repository.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@CrossOrigin(origins = ["http://localhost:4523"])
@RestController
@RequestMapping("/todos")
class TodoController {
    @Autowired
    lateinit var repository: TodoRepository

    @GetMapping
    fun findAll(): List<TodoResponse> {
        return repository.findAll().map { todo -> responseMapper(todo) }
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    fun add(@RequestBody todoRequest: TodoRequest): TodoResponse {
        return responseMapper(repository.save(requestMapper(todoRequest)))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): Optional<TodoResponse> {
        val singleTodo = repository.findById(id)
        if (singleTodo.isPresent) {
            return singleTodo.map { todo -> responseMapper((todo))}
        } else {
            throw NotFoundException("")
        }
    }

    @PutMapping("/{id}")
    fun updateById(@PathVariable(value = "id") id: Int, @Valid @RequestBody newTodoRequest: TodoRequest): Optional<TodoResponse> {
        return repository.findById(id).map { existingTodo ->
            val updatedTodo = existingTodo.copy(name = newTodoRequest.name)
            responseMapper(repository.save(updatedTodo))
        }
    }

    @PutMapping("/{id}/toggle_complete")
    fun toggleComplete(@PathVariable(value = "id") id: Int): Optional<TodoResponse> {
        return repository.findById(id).map { existingTodo ->
            val toggledTodo = existingTodo.copy(completed = !existingTodo.completed)
            responseMapper(repository.save(toggledTodo))
        }
    }

    @DeleteMapping("/{id}")
    fun remove(@PathVariable id: Int) {
        val singleTodo = repository.findById(id)
        if (singleTodo.isPresent) {
            return repository.deleteById(id)
        } else {
            throw NotFoundException("")
        }
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "todo does not exist")
    class NotFoundException(message: String): RuntimeException(message)
}


