package com.taurin.minpaku.unit.controller

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.infrastructure.Entity.Profile
import com.taurin.minpaku.presentation.reservation.ReservationFormController
import com.taurin.minpaku.service.AuthService
import com.taurin.minpaku.service.ProfileService
import com.taurin.minpaku.service.ReserveService
import io.mockk.MockKAnnotations
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(ReservationFormController::class)
class ReservationFormControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @MockkBean
    private lateinit var authService: AuthService

    @MockkBean
    private lateinit var reserveService: ReserveService

    @MockkBean
    private lateinit var profileService: ProfileService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testShowReservationForm() {
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
            MockMvcRequestBuilders.get("/reservation/form")
            .with(user("test"))
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(view().name("reservation/form"))
    }

    @Test
    fun testShowReservationFormWithoutProfile() {
        every {
            authService.loadUserByUsername(any())
        } returns User.withUsername("test")
            .password(passwordEncoder.encode("test"))
            .roles("ADMIN")
            .build()

        mockMvc.perform(
            MockMvcRequestBuilders.get("/reservation/form")
                .with(user("test"))
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(view().name("not_found"))
            .andExpect(model().attributeExists("error"))
    }
}