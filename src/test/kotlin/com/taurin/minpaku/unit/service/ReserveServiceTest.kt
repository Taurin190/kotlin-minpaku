package com.taurin.minpaku.unit.service

import com.taurin.minpaku.domain.reservation.*
import com.taurin.minpaku.infrastructure.Entity.Reservation
import com.taurin.minpaku.infrastructure.Repository.ReserveRepository
import com.taurin.minpaku.infrastructure.datasource.ReserveDataSource
import com.taurin.minpaku.infrastructure.exception.DBException
import com.taurin.minpaku.service.ReserveService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import com.taurin.minpaku.domain.reservation.Reservation as ReservationDomain

class ReserveServiceTest {
    @MockK
    private lateinit var reserveRepository: ReserveRepository

    @MockK
    private lateinit var reserveDataSource: ReserveDataSource

    @InjectMockKs
    private lateinit var reserveService: ReserveService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testGetReservationsInMonth() {
        val reservations = Reservations()
        val reservation = ReservationDomain(
            Title("Test Taro"),
            CheckInDateTime(
                LocalDateTime.parse("2020-05-01T15:00:00"),
            ),
            CheckOutDateTime(
                LocalDateTime.parse("2020-05-03T10:00:00"),
            ),
            null
        )
        reservations.append(reservation)

        every {
            reserveDataSource.findAllByDuration(any(), any())
        } returns reservations

        val actual = reserveService.getReservationsInMonth(2021, 5)
        assertThat(actual.count()).isEqualTo(1)
    }

    @Test
    fun testReserve() {
        val reservation = Reservation(10)
        every {
            reserveRepository.save(any())
        } returns reservation
        reserveService.reserve(reservation)
        verify {
            reserveRepository.save(any())
        }
    }

    @Test
    fun testReserveWithException() {
        val reservation = Reservation(10)
        every {
            reserveRepository.save(any())
        } throws Exception("Test Exception")
        try {
            reserveService.reserve(reservation)
        } catch (e: DBException) {
            assertThat(e.message).isEqualTo("登録出来ませんでした。")
        }
    }

    @Test
    fun testRegister() {
        val reservationDomain = ReservationDomain(
            Title("Test Reservation"),
            CheckInDateTime(LocalDateTime.parse("2021-01-01T15:00:00")),
            CheckOutDateTime(LocalDateTime.parse("2021-01-03T10:00:00")),
            Url("http://localhost/test")
        )

        every {
            reserveRepository.save(any())
        } returns ReservationDomain.toEntity(reservationDomain)

        reserveService.register(reservationDomain)

        verify {
            reserveRepository.save(any())
        }
    }

    @Test
    fun testRegisterWithException() {
        val reservationDomain = ReservationDomain(
            Title("Test Reservation"),
            CheckInDateTime(LocalDateTime.parse("2021-01-01T15:00:00")),
            CheckOutDateTime(LocalDateTime.parse("2021-01-03T10:00:00")),
            Url("http://localhost/test")
        )

        every {
            reserveRepository.save(any())
        } throws Exception("Test Exception")
        try {
            reserveService.register(reservationDomain)
        } catch (e: DBException) {
            assertThat(e.message).isEqualTo("登録出来ませんでした。")
        }
    }
}