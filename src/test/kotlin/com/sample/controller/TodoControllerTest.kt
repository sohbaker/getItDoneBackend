package com.sample.controller

import com.jayway.jsonpath.JsonPath
import com.sample.TestHelper
import com.sample.model.TodoResponse
import org.json.JSONObject

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class TodoControllerTest(
    @Autowired
    val testRestTemplate: TestRestTemplate
) {
    @Autowired
    lateinit var testHelper: TestHelper
    lateinit var httpHeaders: HttpHeaders

    @BeforeEach
    fun setUp() {
        testRestTemplate.postForEntity<Void>("/reset", String::class.java)
        httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        httpHeaders.accept = listOf(MediaType.APPLICATION_JSON)
    }

    @Test
    fun `if there are no tasks to find the user receives an empty list`() {
        val result = testRestTemplate.getForEntity("/todos", List::class.java)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertTrue(result.body!!.isEmpty())
    }

    @Test
    fun `user can create a new task`() {
        val requestBody = JSONObject().put("name", testHelper.TASK_ONE().name).toString()
        val todo = testRestTemplate.postForEntity("/todos", HttpEntity(requestBody, httpHeaders), TodoResponse::class.java)

        val result = testRestTemplate.getForEntity("/todos/${todo.body!!.id}", String::class.java)
        assertTrue(result.body!!.contains(testHelper.TASK_ONE().name))
    }

    @Test
    fun `find tasks for a user if there are any`() {
        val requestBody = JSONObject().put("name", testHelper.TASK_ONE().name).toString()
        testRestTemplate.postForEntity("/todos", HttpEntity(requestBody, httpHeaders), TodoResponse::class.java)

        val result = testRestTemplate.getForEntity("/todos", String::class.java)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertTrue(result?.body!!.contains(testHelper.TASK_ONE().name))
    }

    @Test
    fun `user can find a specific task`() {
        val requestBody = JSONObject().put("name", testHelper.TASK_TWO().name).toString()
        val todo = testRestTemplate.postForEntity("/todos", HttpEntity(requestBody, httpHeaders), TodoResponse::class.java)

        val result = testRestTemplate.getForEntity("/todos/${todo.body!!.id}", String::class.java)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertTrue(result.body!!.contains(testHelper.TASK_TWO().name))
    }

    @Test
    fun `user can update the description of a task`() {
        val requestBody = JSONObject().put("name", testHelper.TASK_TWO().name).toString()
        val todo = testRestTemplate.postForEntity("/todos", HttpEntity(requestBody, httpHeaders), TodoResponse::class.java)

        val string = "new details"
        val updatedRequestBody = JSONObject().put("name", string).toString()
        testRestTemplate.put("/todos/${todo.body!!.id}", HttpEntity(updatedRequestBody, httpHeaders))

        val result = testRestTemplate.getForEntity("/todos/${todo.body!!.id}", String::class.java)

        assertTrue(result.body!!.contains(string))
    }

    @Test
    fun `user can mark a task as complete`() {
        val requestBody = JSONObject().put("name", testHelper.TASK_TWO().name).toString()
        val todo = testRestTemplate.postForEntity("/todos", HttpEntity(requestBody, httpHeaders), TodoResponse::class.java)

        val updatedRequestBody = JSONObject().put("completed", true).toString()
        testRestTemplate.put("/todos/${todo.body!!.id}/toggle_complete", HttpEntity(updatedRequestBody, httpHeaders))

        val result = testRestTemplate.getForEntity("/todos/${todo.body!!.id}", String::class.java)

        assertTrue(result.body!!.contains("true"))
    }

    @Test
    fun `user can delete an existing task`() {
        val requestBody = JSONObject().put("name", testHelper.TASK_TWO().name).toString()
        val todo = testRestTemplate.postForEntity("/todos", HttpEntity(requestBody, httpHeaders), TodoResponse::class.java)

        testRestTemplate.delete("/todos/${todo.body!!.id}")

        val result = testRestTemplate.getForEntity("/todos/${todo.body!!.id}", String::class.java)

        assertFalse(result.body!!.contains(testHelper.TASK_TWO().name))
    }
}
