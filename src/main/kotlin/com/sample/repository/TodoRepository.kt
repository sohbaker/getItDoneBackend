package com.sample.repository

import com.sample.model.Todo
import org.springframework.data.repository.CrudRepository

interface TodoRepository : CrudRepository<Todo, Int> {

}
