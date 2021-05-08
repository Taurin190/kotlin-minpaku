package com.taurin.minpaku.Integration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["application.runner.enabled=false"]
)
class AuthControllerTest(@Autowired val restTemplate: TestRestTemplate) {
    @Test
    fun testLogin() {
        val entity = restTemplate.getForEntity<String>("/login")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun testLogout() {
        val entity = restTemplate.getForEntity<String>("/logout")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun testRegister() {
        val entity = restTemplate.getForEntity<String>("/register")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }
}