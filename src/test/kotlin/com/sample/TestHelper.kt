package com.sample

import com.sample.model.Todo
import org.springframework.context.annotation.Configuration

@Configuration
class TestHelper {
    fun todo(
            id: Int,
            name: String,
            completed: Boolean
    ) = Todo(
            id = id,
            name = name,
            completed = completed
    )

    fun MOCK_TODO_ONE() : Todo {
        return todo(1, "a test task", false)
    }

    fun MOCK_TODO_TWO() : Todo {
        return todo(2, "testing again", true)
    }
}