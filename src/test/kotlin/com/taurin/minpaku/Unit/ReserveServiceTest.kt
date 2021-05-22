package com.taurin.minpaku.Unit

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.Entity.Reservation
import com.taurin.minpaku.Repository.ReserveRepository
import com.taurin.minpaku.Service.ReserveService
import io.mockk.MockKAnnotations
import io.mockk.every
import org.assertj.core.api.Assertions
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
}