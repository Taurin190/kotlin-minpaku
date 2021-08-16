package com.taurin.minpaku.unit.controller

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.presentation.user.LoginController
import com.taurin.minpaku.service.AuthService
import io.mockk.MockKAnnotations
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(LoginController::class)
class LoginControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @MockkBean
    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testLoginForm() {
        mockMvc.perform(
            get("/login"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is2xxSuccessful)
    }

    @Test
    fun testPostAuthLogin() {
        every {
            authService.loadUserByUsername(any())
        } returns User.withUsername("test")
            .password(passwordEncoder.encode("test"))
            .roles("ADMIN")
            .build()

        mockMvc.perform(
            SecurityMockMvcRequestBuilders.formLogin()
                .loginProcessingUrl("/login")
                .user("test")
                .password("test"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/"))
            .andExpect(authenticated().withUsername("test"))
    }

    @Test
    fun testPostAuthLoginWithInvalidAccount() {
        every {
            authService.loadUserByUsername(any())
        } returns User.withUsername("test")
            .password(passwordEncoder.encode("test"))
            .roles("ADMIN")
            .build()

        mockMvc.perform(
            SecurityMockMvcRequestBuilders.formLogin()
                .loginProcessingUrl("/login")
                .user("test")
                .password("invalid"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/login"))
            .andExpect(request().sessionAttribute("error", "ユーザ名またはパスワードが正しくありません"))
    }

    @Test
    fun testPasswordReset() {
        mockMvc.perform(
            get("/password/reset"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is2xxSuccessful)
    }
}