package com.taurin.minpaku.unit.service

import com.taurin.minpaku.infrastructure.Entity.Reservation
import com.taurin.minpaku.infrastructure.exception.DBException
import com.taurin.minpaku.infrastructure.Repository.ReserveRepository
import com.taurin.minpaku.service.ReserveService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ReserveServiceTest {
    @MockK
    private lateinit var reserveRepository: ReserveRepository

    @InjectMockKs
    private lateinit var reserveService: ReserveService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testGetReservationsByDuration() {
        val reservation = Reservation(10)
        every {
            reserveRepository.findAllByDuration(
                any(),
                any()
            )
        } returns listOf(reservation)
        val actual = reserveService.getReservationsByDuration(2021,  5)
        Assertions.assertThat(actual.count()).isEqualTo(1)
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
}