package com.sample

import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class SpringKotlinApplicationTest {
	@Test
	fun loadsApplicationSuccessfully() {
		println("Loaded successfully")
	}
}
