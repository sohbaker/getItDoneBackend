package com.sample

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(classes = arrayOf(SpringKotlinApplication::class), webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringKotlinApplicationTest {
	@Autowired
	lateinit var testRestTemplate: TestRestTemplate

	@Test
	fun loadsApplicationSuccessfully() {
		val result = testRestTemplate
				.getForEntity("/", String::class.java)

		assertEquals(HttpStatus.OK, result?.statusCode)
	}
}
