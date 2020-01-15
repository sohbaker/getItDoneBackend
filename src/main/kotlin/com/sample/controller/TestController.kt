package com.sample.controller

import com.sample.repository.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@CrossOrigin(origins = ["http://localhost:4523"])
@RestController
@RequestMapping
class TestController {
    @Autowired
    lateinit var repository: TodoRepository

    @PostMapping("/reset")
    fun reset() {
        return repository.deleteAll()
    }
}