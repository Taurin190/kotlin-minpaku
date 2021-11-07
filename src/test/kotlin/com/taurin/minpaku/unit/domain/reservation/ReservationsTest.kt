package com.taurin.minpaku.unit.domain.reservation

import com.taurin.minpaku.domain.model.reservation.*
import com.taurin.minpaku.domain.model.user.Profile
import com.taurin.minpaku.domain.model.user.User
import com.taurin.minpaku.helper.Factory
import com.taurin.minpaku.helper.ProfileFactory
import com.taurin.minpaku.helper.ReservationFactory
import com.taurin.minpaku.helper.UserFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ReservationsTest {
    @BeforeEach
    fun setUp() {
        Factory.define("Reservation", ReservationFactory.make())
        Factory.define("Profile", ProfileFactory.make())
        Factory.define("User", UserFactory.make())
    }

    @Test
    fun testReservationsString() {
        val reservations = Reservations()
        val profile = Factory.make("Profile", mapOf(
            "name" to "Test Taro"
        )) as Profile
        val user = Factory.make("User", mapOf(
            "profile" to profile
        )) as User
        reservations.append(
            Factory.make("Reservation",
                mapOf(
                    "title" to "Test Reservation",
                    "check_in_datetime" to "2021-01-01T15:00:00",
                    "check_out_datetime" to "2021-01-03T10:00:00",
                    "url" to "http://localhost/test",
                    "user" to user
                )
            ) as Reservation
        )
        val profile2 = Factory.make("Profile", mapOf(
            "name" to "Test Taro2"
        )) as Profile
        val user2 = Factory.make("User", mapOf(
            "profile" to profile2,
            "user_name" to "testtaro2",
            "email_address" to "test2@mail.com"
        )) as User
        reservations.append(
            Factory.make("Reservation",
                mapOf(
                    "title" to "Test Reservation2",
                    "check_in_datetime" to "2021-01-04T15:00:00",
                    "check_out_datetime" to "2021-01-07T10:00:00",
                    "url" to "http://localhost/test2",
                    "user" to user2
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
        val profile = Factory.make("Profile", mapOf(
            "name" to "Test Taro"
        )) as Profile
        val user = Factory.make("User", mapOf(
            "profile" to profile
        )) as User
        reservations.append(
            Factory.make("Reservation",
                mapOf(
                    "title" to "Test Reservation",
                    "check_in_datetime" to "2021-01-01T15:00:00",
                    "check_out_datetime" to "2021-01-03T10:00:00",
                    "url" to "http://localhost/test",
                    "user" to user
                )
            ) as Reservation
        )
        val profile2 = Factory.make("Profile", mapOf(
            "name" to "Test Taro2"
        )) as Profile
        val user2 = Factory.make("User", mapOf(
            "profile" to profile2,
            "user_name" to "testtaro2",
            "email_address" to "test2@mail.com"
        )) as User
        reservations.append(
            Factory.make("Reservation",
                mapOf(
                    "title" to "Test Reservation2",
                    "check_in_datetime" to "2021-01-04T15:00:00",
                    "check_out_datetime" to "2021-01-07T10:00:00",
                    "url" to "http://localhost/test2",
                    "user" to user2
                )
            ) as Reservation
        )

        reservations.forEach {
            assertThat(it.toString()).contains("Test Taro")
        }
    }
}