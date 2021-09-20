package com.taurin.minpaku.unit.entity

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
}