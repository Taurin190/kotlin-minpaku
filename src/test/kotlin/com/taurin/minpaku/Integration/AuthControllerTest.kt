package com.taurin.minpaku.Integration

import com.taurin.minpaku.Form.LoginForm
import com.taurin.minpaku.Form.RegisterForm
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["application.runner.enabled=true"]
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
    fun testShowRegisterPage() {
        val entity = restTemplate.getForEntity<String>("/register")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun testLoginWithAdminUser() {
        var form = LoginForm()
        form.username = "admin"
        form.password = "password"
        val entity = restTemplate.postForEntity("/login", form, String::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.FOUND)
    }

    @Test
    fun testRegisterWithDuplicated() {
        var form = RegisterForm()
        form.username = "admin"
        form.password = "admin"
        val entity = restTemplate.postForEntity("/register", form, String::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.FOUND)
    }
}