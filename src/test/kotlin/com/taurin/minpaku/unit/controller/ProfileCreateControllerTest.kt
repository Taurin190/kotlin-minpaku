package com.taurin.minpaku.unit.controller

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.infrastructure.Entity.Profile
import com.taurin.minpaku.presentation.user.ProfileCreateController
import com.taurin.minpaku.presentation.user.ProfileNotFound
import com.taurin.minpaku.service.AuthService
import com.taurin.minpaku.service.ProfileService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(ProfileCreateController::class)
class ProfileCreateControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @MockkBean
    private lateinit var authService: AuthService

    @MockkBean
    private lateinit var profileService: ProfileService

    @Test
    fun testShowFormWithoutAuthentication() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/profile/form")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"))
    }

    @Test
    fun testShowForm() {
        every {
            authService.loadUserByUsername(any())
        } returns User.withUsername("test")
            .password(passwordEncoder.encode("test"))
            .roles("ADMIN")
            .build()

        every {
            profileService.findByUsername("test")
        } throws ProfileNotFound("")

        mockMvc.perform(get("/profile/form")
                .with(user("test"))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andExpect(MockMvcResultMatchers.model()
                .attributeExists("profileForm")
            )
    }

    @Test
    fun testShowWithRegisteredProfile() {
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
            profileService.findByUsername("test")
        } returns profile

        mockMvc.perform(get("/profile/form")
            .with(user("test"))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.redirectedUrl("/profile"))
    }
}