package com.taurin.minpaku.unit.domain.reservation

import com.taurin.minpaku.domain.model.reservation.*
import com.taurin.minpaku.domain.model.user.*
import com.taurin.minpaku.domain.type.Permission
import com.taurin.minpaku.helper.Factory
import com.taurin.minpaku.helper.ReservationFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ReservationsTest {
    @BeforeEach
    fun setUp() {
        Factory.define("Reservation", ReservationFactory.make())
    }

    @Test
    fun testReservationsString() {
        val reservations = Reservations()
        reservations.append(
            Factory.make("Reservation",
                mapOf(
                    "title" to "Test Reservation",
                    "check_in_datetime" to "2021-01-01T15:00:00",
                    "check_out_datetime" to "2021-01-03T10:00:00",
                    "url" to "http://localhost/test",
                    "user_name" to "testtaro",
                    "profile_name" to "Test Taro",
                    "email_address" to "test@mail.com"
                )
            ) as Reservation
        )
        reservations.append(
            Factory.make("Reservation",
                mapOf(
                    "title" to "Test Reservation2",
                    "check_in_datetime" to "2021-01-04T15:00:00",
                    "check_out_datetime" to "2021-01-07T10:00:00",
                    "url" to "http://localhost/test2",
                    "user_name" to "testtaro2",
                    "profile_name" to "Test Taro2",
                    "email_address" to "test2@mail.com"
                )
            ) as Reservation
        )

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
        reservations.append(
            Factory.make("Reservation",
                mapOf(
                    "title" to "Test Reservation",
                    "check_in_datetime" to "2021-01-01T15:00:00",
                    "check_out_datetime" to "2021-01-03T10:00:00",
                    "url" to "http://localhost/test",
                    "user_name" to "testtaro",
                    "profile_name" to "Test Taro",
                    "email_address" to "test@mail.com"
                )
            ) as Reservation
        )
        reservations.append(
            Factory.make("Reservation",
                mapOf(
                    "title" to "Test Reservation2",
                    "check_in_datetime" to "2021-01-04T15:00:00",
                    "check_out_datetime" to "2021-01-07T10:00:00",
                    "url" to "http://localhost/test2",
                    "user_name" to "testtaro2",
                    "profile_name" to "Test Taro2",
                    "email_address" to "test2@mail.com"
                )
            ) as Reservation
        )

        reservations.forEach {
            assertThat(it.toString()).contains("Test Taro")
        }
    }
}