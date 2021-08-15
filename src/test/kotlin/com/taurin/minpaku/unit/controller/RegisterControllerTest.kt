package com.taurin.minpaku.unit.controller

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.presentation.user.RegisterController
import com.taurin.minpaku.presentation.user.RegisterForm
import com.taurin.minpaku.service.AuthService
import io.mockk.MockKAnnotations
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@ExtendWith(SpringExtension::class)
@WebMvcTest(RegisterController::class)
class RegisterControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testShowRegisterForm() {
        mockMvc.perform(
            get("/register"))
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
    }

    @Test
    fun testPostRegister() {
        val registerForm = RegisterForm()
        registerForm.username = "test.taro"
        registerForm.password = "password"
        registerForm.passwordMatch = "password"

        every {
            authService.register(any(), any())
        } returns Unit

        mockMvc.perform(
            post("/register")
            .flashAttr("registerForm", registerForm)
            .with(csrf())
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(view().name("register/complete"))
    }

    @Test
    fun testPostRegisterWithInvalidValues() {
        val registerForm = RegisterForm()
        registerForm.username = "test"
        registerForm.password = "password"
        registerForm.passwordMatch = "p@ssw0rd"

        every {
            authService.register(any(), any())
        } returns Unit

        mockMvc.perform(
            post("/register")
                .flashAttr("registerForm", registerForm)
                .with(csrf())
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(view().name("register"))
    }
}