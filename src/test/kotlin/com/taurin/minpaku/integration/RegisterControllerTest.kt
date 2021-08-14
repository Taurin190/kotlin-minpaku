package com.taurin.minpaku.integration

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["application.runner.enabled=true"]
)
@AutoConfigureMockMvc
class RegisterControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testLogin() {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

    @Test
    fun testLogout() {
        mockMvc.perform(MockMvcRequestBuilders.get("/logout"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(redirectedUrl("/login"))
    }

    @Test
    fun testShowRegisterPage() {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

    @Test
    fun testLoginWithAdminUser() {
        mockMvc.perform(
            formLogin("/login")
                .user("admin")
                .password("password"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(redirectedUrl("/"))
    }

    @Test
    fun testLoginWithAdminUserWithWrongPassword() {
        mockMvc.perform(
            formLogin("/login")
                .user("admin")
                .password("wrongpassword"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(redirectedUrl("/login"))
    }

    @Test
    fun testRegisterWithInvalidParams() {
        mockMvc.perform(
            post("/register")
                .param("username", "admin")
                .param("password", "admin"))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }
}