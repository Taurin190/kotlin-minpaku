package com.taurin.minpaku.unit.controller

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.domain.type.Permission
import com.taurin.minpaku.presentation.admin.AdminController
import com.taurin.minpaku.presentation.user.LoginController
import com.taurin.minpaku.service.AuthService
import io.mockk.MockKAnnotations
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(AdminController::class)
class AdminControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @MockkBean
    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testAccessAdminWithoutLogin() {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/admin"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login"))
    }

    @Test
    fun testAccessAdminWithUserAccount() {
        every {
            authService.loadUserByUsername(any())
        } returns User.withUsername("test123")
                .password(passwordEncoder.encode("test123"))
                .roles("USER")
                .build()

        mockMvc.perform(
                MockMvcRequestBuilders.get("/admin")
                        .with(user("test123")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun testAccessAdminWithAdminAccount() {
        every {
            authService.loadUserByUsername(any())
        } returns User.withUsername("test999")
                .password(passwordEncoder.encode("test999"))
                .roles("ADMIN")
                .build()

        mockMvc.perform(
                MockMvcRequestBuilders.get("/admin")
                        .with(user("test999").roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }
}