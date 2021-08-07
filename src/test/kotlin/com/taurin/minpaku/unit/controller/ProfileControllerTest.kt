package com.taurin.minpaku.unit.controller

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.data.Entity.Profile
import com.taurin.minpaku.exception.ProfileNotFound
import com.taurin.minpaku.presentation.controller.ProfileController
import com.taurin.minpaku.service.AuthService
import com.taurin.minpaku.service.ProfileService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(ProfileController::class)
class ProfileControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @MockkBean
    private lateinit var authService: AuthService

    @MockkBean
    private lateinit var profileService: ProfileService

    @Test
    fun testShowProfile() {
        val profile = Profile(
            null,
            null,
            "test",
            "test@gmail.com",
            "address",
            "1234567890"
        )

        every {
            authService.loadUserByUsername(any())
        } returns User.withUsername("test")
            .password(passwordEncoder.encode("test"))
            .roles("ADMIN")
            .build()

        every {
            profileService.findByUsername(any())
        } returns profile

        mockMvc.perform(
            get("/profile")
                .with(user("test")))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andExpect(MockMvcResultMatchers.model()
                .attribute("profile", profile)
            )
    }

    @Test
    fun testShowWithoutProfile() {
        every {
            authService.loadUserByUsername(any())
        } returns User.withUsername("test")
            .password(passwordEncoder.encode("test"))
            .roles("ADMIN")
            .build()

        every {
            profileService.findByUsername(any())
        } throws ProfileNotFound("ユーザ情報が設定されていません。")

        mockMvc.perform(
            get("/profile")
                .with(user("test")))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun testShowWithoutAuthentication() {
        mockMvc.perform(
            get("/profile"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("http://localhost/login"))
    }
}