package com.taurin.minpaku.unit.controller

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.infrastructure.Entity.Profile
import com.taurin.minpaku.presentation.user.ProfileCreateController
import com.taurin.minpaku.presentation.user.ProfileForm
import com.taurin.minpaku.presentation.user.ProfileNotFound
import com.taurin.minpaku.service.AuthService
import com.taurin.minpaku.service.ProfileService
import com.taurin.minpaku.service.UserService
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

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

    @MockkBean
    private lateinit var userService: UserService

    @Test
    fun testShowFormWithoutAuthentication() {
        mockMvc.perform(
            get("/profile/form")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("http://localhost/login"))
    }

    @Test
    fun testShowForm() {
        every {
            authService.loadUserByUsername(any())
        } returns User.withUsername("test123")
            .password(passwordEncoder.encode("test"))
            .roles("ADMIN")
            .build()

        every {
            userService.confirmProfileByUserName("test123")
        } returns false

        mockMvc.perform(get("/profile/form")
                .with(user("test123"))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(model()
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
            userService.confirmProfileByUserName("test")
        } returns true

        mockMvc.perform(get("/profile/form")
            .with(user("test"))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/profile"))
    }

    @Test
    fun testPostWithoutAuthentication() {
        mockMvc.perform(post("/profile/confirm")
            .with(csrf())
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("http://localhost/login"))
    }

    @Test
    fun testPostWithValidProfile() {
        val profileForm = ProfileForm()
        profileForm.name = "test"
        profileForm.address = "address"
        profileForm.email = "test@mail.com"
        profileForm.phone = "12345"

        every {
            authService.loadUserByUsername(any())
        } returns User.withUsername("test")
            .password(passwordEncoder.encode("test"))
            .roles("ADMIN")
            .build()

        mockMvc.perform(post("/profile/confirm")
            .flashAttr("profileForm", profileForm)
            .with(user("test"))
            .with(csrf())
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(view().name("profile/confirm"))
    }

    @Test
    fun testPostCompleteWithValidProfile() {
        val profileForm = ProfileForm()
        profileForm.name = "test"
        profileForm.address = "address"
        profileForm.email = "test@mail.com"
        profileForm.phone = "12345"

        every {
            authService.loadUserByUsername(any())
        } returns User.withUsername("test")
            .password(passwordEncoder.encode("test"))
            .roles("ADMIN")
            .build()

        every {
            profileService.register(any(), any(), any(), any(), any())
        } returns Unit

        mockMvc.perform(post("/profile/complete")
            .flashAttr("profileForm", profileForm)
            .with(user("test"))
            .with(csrf())
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(view().name("profile/complete"))

        verify(exactly = 1) {
            profileService.register(any(), any(), any(), any(), any())
        }
    }
}