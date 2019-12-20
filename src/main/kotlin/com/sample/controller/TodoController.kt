package com.sample.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController {
    val TODOS = mutableListOf("drink water", "feed the cat", "buy food")

    @GetMapping("/todos")
    fun showTodos(): List<String> {
        return TODOS
    }
}
