package com.sample.controller

import com.sample.TestHelper
import com.sample.model.Todo
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

    @BeforeEach
    fun clearTaskList() {
        testRestTemplate.postForEntity<Void>("/reset", String::class.java)
    }

    @Test
    fun `if there are no tasks to find the user receives an empty list`() {
        val result = testRestTemplate.getForEntity("/todos", List::class.java)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertTrue(result.body!!.isEmpty())
    }

    @Test
    fun `user can create a new task`() {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        httpHeaders.accept = listOf(MediaType.APPLICATION_JSON)

        val requestBody = JSONObject().put("name", testHelper.MOCK_TODO_ONE().name)
        val todo = testRestTemplate.postForEntity("/todos", HttpEntity(requestBody.toString(), httpHeaders), Todo::class.java)

        val result = testRestTemplate.getForEntity("/todos/${todo.body!!.id}", String::class.java)
        assertTrue(result.body!!.contains(testHelper.MOCK_TODO_ONE().name))
    }

    @Test
    fun `find tasks for a user if there are any`() {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        httpHeaders.accept = listOf(MediaType.APPLICATION_JSON)

        val requestBody = JSONObject().put("name", testHelper.MOCK_TODO_ONE().name)
        testRestTemplate.postForEntity("/todos", HttpEntity(requestBody.toString(),httpHeaders), Todo::class.java)

        val result = testRestTemplate.getForEntity("/todos", String::class.java)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertTrue(result?.body!!.contains(testHelper.MOCK_TODO_ONE().name))
    }

    @Test
    fun `user can find a specific task`() {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        httpHeaders.accept = listOf(MediaType.APPLICATION_JSON)

        val requestBody = JSONObject().put("name", testHelper.MOCK_TODO_ONE().name)
        testRestTemplate.postForEntity("/todos", HttpEntity(requestBody.toString(),httpHeaders), Todo::class.java)

        val requestBodyTwo = JSONObject().put("name", testHelper.MOCK_TODO_TWO().name)
        val todoTwo = testRestTemplate.postForEntity("/todos", HttpEntity(requestBodyTwo.toString(),httpHeaders), Todo::class.java)

        val result = testRestTemplate.getForEntity("/todos/${todoTwo.body!!.id}", String::class.java)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertTrue(result.body!!.contains(testHelper.MOCK_TODO_TWO().name))
    }
}
