package com.taurin.minpaku.unit.domain

import com.taurin.minpaku.domain.model.reservation.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ReservationsTest {
    @Test
    fun testReservationsJson() {
        val reservations = Reservations()
        reservations.append(Reservation(
            Title("Test Reservation"),
            CheckInDateTime(LocalDateTime.parse("2021-01-01T15:00:00")),
            CheckOutDateTime(LocalDateTime.parse("2021-01-03T10:00:00")),
            Url("http://localhost/test")
        ))
        reservations.append(Reservation(
            Title("Test Reservation2"),
            CheckInDateTime(LocalDateTime.parse("2021-01-04T15:00:00")),
            CheckOutDateTime(LocalDateTime.parse("2021-01-07T10:00:00")),
            Url("http://localhost/test2")
        ))

        val actual = reservations.toJson()
        assertThat(actual).isEqualTo("[" +
                "{\"title\": \"Test Reservation\"," +
                "\"start\": \"2021-01-01\"," +
                "\"end\": \"2021-01-03\"," +
                "\"url\": \"http://localhost/test\"}," +
                "{\"title\": \"Test Reservation2\"," +
                "\"start\": \"2021-01-04\"," +
                "\"end\": \"2021-01-07\"," +
                "\"url\": \"http://localhost/test2\"}" +
                "]")
    }
}