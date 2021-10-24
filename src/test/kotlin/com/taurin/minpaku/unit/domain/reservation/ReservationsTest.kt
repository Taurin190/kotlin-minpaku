package com.taurin.minpaku.unit.domain.reservation

import com.taurin.minpaku.domain.model.reservation.*
import com.taurin.minpaku.domain.model.user.*
import com.taurin.minpaku.domain.type.Permission
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ReservationsTest {
    @Test
    fun testReservationsString() {
        val reservations = Reservations()
        reservations.append(Reservation(
            Title("Test Reservation"),
            CheckInDateTime(LocalDateTime.parse("2021-01-01T15:00:00")),
            CheckOutDateTime(LocalDateTime.parse("2021-01-03T10:00:00")),
            Url("http://localhost/test"),
            User(
                UserName("testtaro"),
                Profile(
                    Name("Test Taro"),
                    EmailAddress("test@mail.com"),
                    PhoneNumber("090-1234-5678")
                ),
                Permission.USER
            )
        ))
        reservations.append(Reservation(
            Title("Test Reservation2"),
            CheckInDateTime(LocalDateTime.parse("2021-01-04T15:00:00")),
            CheckOutDateTime(LocalDateTime.parse("2021-01-07T10:00:00")),
            Url("http://localhost/test2"),
            User(
                UserName("testtaro2"),
                Profile(
                    Name("Test Taro2"),
                    EmailAddress("test2@mail.com"),
                    PhoneNumber("090-1234-5678")
                ),
                Permission.USER
            )
        ))

        val actual = reservations.toString()
        assertThat(actual).isEqualTo("Reservations[" +
                "{\"user\": \"Test Taro\"," +
                "\"start\": \"2021-01-01\"," +
                "\"end\": \"2021-01-03\"," +
                "\"url\": \"http://localhost/test\"}," +
                "{\"user\": \"Test Taro2\"," +
                "\"start\": \"2021-01-04\"," +
                "\"end\": \"2021-01-07\"," +
                "\"url\": \"http://localhost/test2\"}" +
                "]")
    }

    @Test
    fun testReservationsAsCollection() {
        val reservations = Reservations()
        reservations.append(Reservation(
            Title("Test Reservation"),
            CheckInDateTime(LocalDateTime.parse("2021-01-01T15:00:00")),
            CheckOutDateTime(LocalDateTime.parse("2021-01-03T10:00:00")),
            Url("http://localhost/test"),
            User(
                UserName("testtaro"),
                Profile(
                    Name("Test Taro"),
                    EmailAddress("test@mail.com"),
                    PhoneNumber("090-1234-5678")
                ),
                Permission.USER
            )
        ))
        reservations.append(Reservation(
            Title("Test Reservation2"),
            CheckInDateTime(LocalDateTime.parse("2021-01-04T15:00:00")),
            CheckOutDateTime(LocalDateTime.parse("2021-01-07T10:00:00")),
            Url("http://localhost/test2"),
            User(
                UserName("testtaro2"),
                Profile(
                    Name("Test Taro2"),
                    EmailAddress("test2@mail.com"),
                    PhoneNumber("090-1234-5678")
                ),
                Permission.USER
            )
        ))

        reservations.forEach {
            assertThat(it.toString()).contains("Test Taro")
        }
    }
}