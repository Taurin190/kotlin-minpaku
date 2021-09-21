package com.taurin.minpaku.unit.entity

import com.taurin.minpaku.domain.reservation.CheckInDateTime
import com.taurin.minpaku.domain.reservation.CheckOutDateTime
import com.taurin.minpaku.domain.reservation.Title
import com.taurin.minpaku.infrastructure.Entity.Book
import com.taurin.minpaku.infrastructure.Entity.Reservation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ReservationTest {
    @Test
    fun testToDomain() {
        val entity = Reservation(
            null,
            null,
            mutableListOf<Book>(),
            LocalDateTime.parse("2021-03-01T17:00:00"),
            LocalDateTime.parse("2021-03-03T08:00:00")
        )

        val actual = entity.toDomain()
        assertThat(actual.toString())
            .isEqualTo("Reservation [title=Guest, start=2021-03-01, end=2021-03-03]")
    }

    @Test
    fun testFromDomain() {
        val reservation = com.taurin.minpaku.domain.reservation.Reservation(
            Title("Test Taro"),
            CheckInDateTime(
                LocalDateTime.parse("2021-07-01T15:00:00"),
            ),
            CheckOutDateTime(
                LocalDateTime.parse("2021-07-03T10:00:00"),
            ),
            null
        )
        val actual = Reservation.fromDomain(reservation)
        assertThat(actual.checkInDateTime.year).isEqualTo(2021)
        assertThat(actual.checkInDateTime.month.value).isEqualTo(7)
        assertThat(actual.checkInDateTime.dayOfMonth).isEqualTo(1)
        assertThat(actual.checkInDateTime.hour).isEqualTo(15)
    }
}