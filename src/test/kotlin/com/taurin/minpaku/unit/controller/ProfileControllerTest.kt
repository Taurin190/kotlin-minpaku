package com.taurin.minpaku.unit.controller

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.helper.Factory
import com.taurin.minpaku.helper.ProfileFactory
import com.taurin.minpaku.helper.ReservationFactory
import com.taurin.minpaku.helper.UserFactory
import com.taurin.minpaku.presentation.user.ProfileController
import com.taurin.minpaku.service.AuthService
import com.taurin.minpaku.service.ProfileService
import com.taurin.minpaku.service.UserService
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
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

    @MockkBean
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        Factory.define("Reservation", ReservationFactory.make())
        Factory.define("Profile", ProfileFactory.make())
        Factory.define("User", UserFactory.make())
    }

    @Test
    fun testShowProfile() {

        val profile = Factory.make("Profile", mapOf(
                "name" to "Test Taro"
        )) as com.taurin.minpaku.domain.model.user.Profile
        val user = Factory.make("User", mapOf(
                "profile" to profile
        )) as com.taurin.minpaku.domain.model.user.User

        every {
            authService.loadUserByUsername(any())
        } returns User.withUsername("test")
            .password(passwordEncoder.encode("test"))
            .roles("ADMIN")
            .build()

        every {
            userService.getByUserName(any())
        } returns user

        mockMvc.perform(
            get("/profile")
                .with(user("test")))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andExpect(MockMvcResultMatchers.model()
                .attributeExists("profile")
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

        val user = Factory.make("User", mapOf())
                as com.taurin.minpaku.domain.model.user.User

        every {
            userService.getByUserName(any())
        } returns user

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