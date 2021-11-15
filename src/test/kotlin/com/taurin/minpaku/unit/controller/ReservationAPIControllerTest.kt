package com.taurin.minpaku.unit.controller

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.domain.model.reservation.CheckInDateTime
import com.taurin.minpaku.domain.model.reservation.CheckOutDateTime
import com.taurin.minpaku.domain.model.reservation.Reservations
import com.taurin.minpaku.domain.model.reservation.Title
import com.taurin.minpaku.domain.model.user.*
import com.taurin.minpaku.domain.type.Permission
import com.taurin.minpaku.helper.Factory
import com.taurin.minpaku.helper.ProfileFactory
import com.taurin.minpaku.helper.ReservationFactory
import com.taurin.minpaku.helper.UserFactory
import com.taurin.minpaku.domain.model.reservation.Reservation as ReservationDomain
import com.taurin.minpaku.presentation.reservation.ReservationAPIController
import com.taurin.minpaku.service.AuthService
import com.taurin.minpaku.service.ReserveService
import io.mockk.MockKAnnotations
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@WebMvcTest(ReservationAPIController::class)
class ReservationAPIControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var reserveService: ReserveService

    @MockkBean
    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        Factory.define("Reservation", ReservationFactory.make())
        Factory.define("Profile", ProfileFactory.make())
        Factory.define("User", UserFactory.make())
    }

    @Test
    fun testShowList() {
        val profile = Factory.make("Profile", mapOf(
                "name" to "Test Taro"
        )) as Profile
        val user = Factory.make("User", mapOf(
                "user_name" to "testtaro",
                "profile" to profile
        )) as User
        val reservationDomain = Factory.make("Reservation",
                mapOf(
                        "title" to "Test Reservation",
                        "check_in_datetime" to "2021-05-01T15:00:00",
                        "check_out_datetime" to "2021-05-03T10:00:00",
                        "user" to user
                )
        ) as ReservationDomain

        val reservations = Reservations()
        reservations.append(reservationDomain)

        every {
            reserveService.getReservationsInMonth(any(), any())
        } returns reservations

        mockMvc.perform(
            get("/api/reservation/list?year=2021&month=5")
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(content().json(
                "[{\"title\": \"Test Taro\",\"start\": \"2021-05-01\",\"end\": \"2021-05-03\"}]"
            ))
    }
}