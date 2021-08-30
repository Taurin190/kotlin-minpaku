package com.taurin.minpaku.unit.domain

import com.taurin.minpaku.domain.reservation.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ReservationTest {
    @Test
    fun testReservationJson() {
        val actual = Reservation(
            Title("Test Reservation"),
            Start("2021-01-01"),
            End("2021-01-03"),
            Url("http://localhost/test")
        )

        assertThat(actual.toJson())
            .isEqualTo("{\"title\":{\"value\":\"Test Reservation\"}," +
                    "\"start\":{\"value\":\"2021-01-01\"}," +
                    "\"end\":{\"value\":\"2021-01-03\"}," +
                    "\"url\":{\"value\":\"http://localhost/test\"}}")
    }

    @Test
    fun testReservationString() {
        val actual = Reservation(
            Title("Test Reservation"),
            Start("2021-01-01"),
            End("2021-01-03"),
            Url("http://localhost/test")
        )

        assertThat(actual.toString())
            .isEqualTo("Reservation [title=Test Reservation, " +
                    "start=2021-01-01, " +
                    "end=2021-01-03, " +
                    "url=http://localhost/test]")
    }
}