package com.taurin.minpaku.Unit

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.Entity.Reservation
import com.taurin.minpaku.Exception.DBException
import com.taurin.minpaku.Repository.ReserveRepository
import com.taurin.minpaku.Service.ReserveService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["application.runner.enabled=false"]
)
class ReserveServiceTest {
    @MockkBean
    private lateinit var reserveRepository: ReserveRepository

    @Autowired
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