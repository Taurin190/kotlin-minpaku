package com.taurin.minpaku.unit.controller

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.infrastructure.Entity.Book
import com.taurin.minpaku.infrastructure.Entity.Reservation
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
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testShowList() {
        val list = mutableListOf<Reservation>()
        val bookList = mutableListOf<Book>()
        val book = Book(1, null, 1, null)
        bookList.add(book)
        val reservation = Reservation()
        reservation.books = bookList
        list.add(reservation)


        every {
            reserveService.getReservationsByDuration(any(), any())
        } returns list

        mockMvc.perform(
            get("/api/reservation/list")
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(content().json(
                "[{\"reservationId\":null,\"userId\":null,\"userName\":null,\"bookList\":[{\"bookId\":1,\"guestNum\":1,\"stayDate\":null}]}]"
            ))
    }
}