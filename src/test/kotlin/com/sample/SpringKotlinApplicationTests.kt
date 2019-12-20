package com.sample

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(classes = arrayOf(SpringKotlinApplication::class), webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringKotlinApplicationTests {
	@Autowired
	lateinit var testRestTemplate: TestRestTemplate

	@Test
	fun returnsAListOfTodosWhenTheTodosPathIsHit() {
		val result = testRestTemplate
				.getForEntity("/todos", String::class.java)

		assertNotNull(result)
		assertEquals(HttpStatus.OK, result?.statusCode)
		assertEquals("[{\"id\":1,\"name\":\"drink water\"},{\"id\":2,\"name\":\"feed the cat\"},{\"id\":3,\"name\":\"buy food\"}]", result?.body)
	}
}
