package com.taurin.minpaku.unit.controller

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.helper.Factory
import com.taurin.minpaku.helper.ProfileFactory
import com.taurin.minpaku.helper.UserFactory
import com.taurin.minpaku.domain.model.user.User as UserDomain
import com.taurin.minpaku.domain.model.user.Profile as ProfileDomain
import com.taurin.minpaku.infrastructure.exception.DBException
import com.taurin.minpaku.presentation.reservation.ReservationForm
import com.taurin.minpaku.presentation.reservation.ReservationFormController
import com.taurin.minpaku.service.AuthService
import com.taurin.minpaku.service.ReserveService
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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        Factory.define("Profile", ProfileFactory.make())
        Factory.define("User", UserFactory.make())
    }

    @Test
    fun testShowReservationForm() {
        val profile = Factory.make("Profile", mapOf(
                "name" to "Test Taro"
        )) as ProfileDomain
        val user = Factory.make("User", mapOf(
                "user_name" to "testtaro",
                "profile" to profile
        )) as UserDomain

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
            get("/reservation/form")
            .with(user("test"))
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(view().name("reservation/form"))
    }

    @Test
    fun testShowReservationFormWithoutProfile() {
        val user = Factory.make("User", mapOf(
                "user_name" to "testtaro",
        )) as UserDomain

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
            get("/reservation/form")
                .with(user("test"))
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(view().name("not_found"))
            .andExpect(model().attributeExists("error"))
    }

    @Test
    fun testPostReservationConfirm() {
        val form = ReservationForm()
        form.checkInDate = "2021-01-01"
        form.checkOutDate = "2021-01-03"
        form.checkInTime = "15:00"
        form.checkOutTime = "10:00"
        form.guestNum = 1

        every {
            authService.loadUserByUsername(any())
        } returns User.withUsername("test")
            .password(passwordEncoder.encode("test"))
            .roles("ADMIN")
            .build()

        mockMvc.perform(
            post("/reservation/confirm")
                .with(user("test"))
                .flashAttr("reservationForm", form)
                .sessionAttr("name", "test")
                .with(csrf())
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(view().name("reservation/confirm"))
            .andExpect(model().attributeExists("reservation"))
    }

    @Test
    fun testPostReservationComplete() {
        val form = ReservationForm()
        form.checkInDate = "2021-01-01"
        form.checkOutDate = "2021-01-03"
        form.guestNum = 1

        val user = User.withUsername("test12")
            .password(passwordEncoder.encode("test"))
            .roles("ADMIN")
            .build()

        val userDomain = Factory.make("User", mapOf(
                "user_name" to "test12",
        )) as UserDomain

        every {
            authService.loadUserByUsername(any())
        } returns user

        every {
            reserveService.register(any())
        } returns Unit

        mockMvc.perform(
            post("/reservation/complete")
                .with(user("test12"))
                .flashAttr("reservationForm", form)
                .sessionAttr("user", userDomain)
                .with(csrf())
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(view().name("reservation/complete"))
    }

    @Test
    fun testPostReservationCompleteWithDBException() {
        val form = ReservationForm()
        form.checkInDate = "2021-01-01"
        form.checkOutDate = "2021-01-03"
        form.guestNum = 1

        val user = User.withUsername("test111")
            .password(passwordEncoder.encode("test"))
            .roles("ADMIN")
            .build()

        val userDomain = Factory.make("User", mapOf(
                "user_name" to "test111",
        )) as UserDomain

        every {
            authService.loadUserByUsername(any())
        } returns user

        every {
            reserveService.reserve(any())
        } throws DBException("登録に失敗しました。")

        mockMvc.perform(
            post("/reservation/complete")
                .with(user("test111"))
                .flashAttr("reservationForm", form)
                .sessionAttr("user", userDomain)
                .with(csrf())
        )
            .andDo(print())
            .andExpect(status().isConflict)
            .andExpect(view().name("reservation/error"))
    }

    @Test
    fun testPostReservationCompleteWithException() {
        val form = ReservationForm()
        form.checkInDate = "2021-01-01"
        form.checkOutDate = "2021-01-03"
        form.guestNum = 1

        val user = User.withUsername("test123")
            .password(passwordEncoder.encode("test"))
            .roles("ADMIN")
            .build()

        val userDomain = Factory.make("User", mapOf(
                "user_name" to "test123",
        )) as UserDomain

        every {
            authService.loadUserByUsername(any())
        } returns user

        every {
            reserveService.reserve(any())
        } throws Exception("例外が発生しました。")

        mockMvc.perform(
            post("/reservation/complete")
                .with(user("test123"))
                .flashAttr("reservationForm", form)
                .sessionAttr("user", userDomain)
                .with(csrf())
        )
            .andDo(print())
            .andExpect(status().isConflict)
            .andExpect(view().name("reservation/error"))
    }
}