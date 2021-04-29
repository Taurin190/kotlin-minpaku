package com.taurin.minpaku.Integration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomeControllerTest(@Autowired val restTemplate: TestRestTemplate) {
    @Test
    fun testHome() {
        val entity = restTemplate.getForEntity<String>("/")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }
}