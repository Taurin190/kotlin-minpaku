package com.taurin.minpaku.unit.domain.reservation

import com.taurin.minpaku.domain.model.InvalidParameterException
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
import org.junit.jupiter.api.fail

class ReservationTest {
    @BeforeEach
    fun setUp() {
        Factory.define("Reservation", ReservationFactory.make())
        Factory.define("Profile", ProfileFactory.make())
        Factory.define("User", UserFactory.make())
    }

    @Test
    fun testReservationJson() {
        val profile = Factory.make("Profile", mapOf(
            "name" to "Test Taro"
        )) as Profile
        val user = Factory.make("User", mapOf(
            "profile" to profile
        )) as User
        val actual = Factory.make("Reservation",
            mapOf(
                "title" to "Test Reservation",
                "check_in_datetime" to "2021-01-01T15:00:00",
                "check_out_datetime" to "2021-01-03T10:00:00",
                "url" to "http://localhost/test",
                "user" to user
            )
        ) as Reservation

        assertThat(actual.toJson())
            .isEqualTo("{\"user\": \"Test Taro\"," +
                    "\"start\": \"2021-01-01\"," +
                    "\"end\": \"2021-01-03\"," +
                    "\"url\": \"http://localhost/test\"}")
    }

    @Test
    fun testReservationString() {
        val profile = Factory.make("Profile", mapOf(
            "name" to "Test Taro"
        )) as Profile
        val user = Factory.make("User", mapOf(
            "profile" to profile
        )) as User
        val actual = Factory.make("Reservation",
            mapOf(
                "title" to "Test Reservation",
                "check_in_datetime" to "2021-01-01T15:00:00",
                "check_out_datetime" to "2021-01-03T10:00:00",
                "url" to "http://localhost/test",
                "user" to user
            )
        ) as Reservation

        assertThat(actual.toString())
            .isEqualTo("Reservation [user=Test Taro, " +
                    "start=2021-01-01, " +
                    "end=2021-01-03, " +
                    "url=http://localhost/test]")
    }

    @Test
    fun testCheckInDateTimeWithInValidRange() {
        // 2000?????????????????????
        try {
            Factory.make("Reservation",
                mapOf(
                    "check_in_datetime" to "1999-12-31T15:00:00",
                    "check_out_datetime" to "2000-01-01T10:00:00"
                )
            ) as Reservation
            fail(AssertionError("??????????????????????????????????????????????????????"))
        } catch (e: Exception) {
            assertThat(e.message).contains("?????????????????????????????????????????????????????????")
        }

        // 2040?????????????????????
        try {
            Factory.make("Reservation",
                mapOf(
                    "check_in_datetime" to "2040-12-31T15:00:00",
                    "check_out_datetime" to "2041-01-01T10:00:00"
                )
            ) as Reservation
            fail(AssertionError("??????????????????????????????????????????????????????"))
        } catch (e: Exception) {
            assertThat(e.message).contains("?????????????????????????????????????????????????????????")
        }
    }

    @Test
    fun testEarlierCheckOutDateThanCheckIn() {
        try {
            Factory.make("Reservation",
                mapOf(
                    "check_in_datetime" to "2021-10-01T15:00:00",
                    "check_out_datetime" to "2021-09-30T10:00:00"
                )
            ) as Reservation
            fail(AssertionError("??????????????????????????????????????????????????????"))
        } catch (e: Exception) {
            assertThat(e.message).isIn("?????????????????????????????????????????????????????????")
        }
    }

    @Test
    fun testInvalidCheckInTime() {
        try {
            Factory.make("Reservation",
                mapOf(
                    "check_in_datetime" to "2021-10-01T14:59:59",
                    "check_out_datetime" to "2021-10-03T10:00:00"
                )
            ) as Reservation
            fail(AssertionError("??????????????????????????????????????????????????????"))
        } catch (e: Exception) {
            assertThat(e.message).contains("?????????????????????????????????????????????????????????")
        }

        try {
            Factory.make("Reservation",
                mapOf(
                    "check_in_datetime" to "2021-10-02T00:00:00",
                    "check_out_datetime" to "2021-10-03T10:00:00"
                )
            ) as Reservation
            fail(AssertionError("??????????????????????????????????????????????????????"))
        } catch (e: Exception) {
            assertThat(e.message).contains("?????????????????????????????????????????????????????????")
        }
    }

    @Test
    fun testInvalidCheckOutTime() {
        // 6????????????????????????????????????
        try {
            Factory.make("Reservation",
                mapOf(
                    "check_in_datetime" to "2021-10-01T17:00:00",
                    "check_out_datetime" to "2021-09-30T05:59:59"
                )
            ) as Reservation
            fail(AssertionError("??????????????????????????????????????????????????????"))
        } catch (e: Exception) {
            assertThat(e.message).contains("?????????????????????????????????????????????????????????")
        }

        // 12?????????????????????????????????
        try {
            Factory.make("Reservation",
                mapOf(
                    "check_in_datetime" to "2021-10-01T17:00:00",
                    "check_out_datetime" to "2021-09-30T12:00:00"
                )
            ) as Reservation
            fail(AssertionError("??????????????????????????????????????????????????????"))
        } catch (e: Exception) {
            assertThat(e.message).contains("?????????????????????????????????????????????????????????")
        }
    }

    @Test
    fun testInvalidDaysOfStay() {
        try {
            Factory.make("Reservation",
                mapOf(
                    "check_in_datetime" to "2021-10-01T23:59:59",
                    "check_out_datetime" to "2021-10-05T06:00:00"
                )
            ) as Reservation
            fail(AssertionError("??????????????????????????????????????????????????????"))
        } catch (e: Exception) {
            assertThat(e.message).contains("?????????????????????????????????????????????????????????")
        }
    }

    @Test
    fun testWithoutCheckOutDateTime() {
        val profile = Factory.make("Profile", mapOf(
            "name" to "Test Taro"
        )) as Profile
        val user = Factory.make("User", mapOf(
            "profile" to profile
        )) as User
        val actual = Factory.make("Reservation",
            mapOf(
                "title" to "Test Reservation",
                "check_in_datetime" to "2021-01-01T15:00:00",
                "url" to "http://localhost/test",
                "user" to user
            )
        ) as Reservation

        assertThat(actual.toJson())
            .isEqualTo("{\"user\": \"Test Taro\"," +
                    "\"start\": \"2021-01-01\"," +
                    "\"end\": \"2021-01-02\"," +
                    "\"url\": \"http://localhost/test\"}")
    }

    @Test
    fun testGetDates() {
        val profile = Factory.make("Profile", mapOf(
                "name" to "Test Taro"
        )) as Profile
        val user = Factory.make("User", mapOf(
                "profile" to profile
        )) as User
        val reservation = Factory.make("Reservation",
                mapOf(
                        "title" to "Test Reservation",
                        "check_in_datetime" to "2021-01-01T15:00:00",
                        "check_out_datetime" to "2021-01-03T10:00:00",
                        "url" to "http://localhost/test",
                        "user" to user
                )
        ) as Reservation

        val actual = reservation.getStayDates()
        // 1???3????????????????????????????????????????????????????????????
        assertThat(actual.size).isEqualTo(2)
    }

    @Test
    fun testCreateFactory() {
        val profile = Factory.make("Profile", mapOf(
                "name" to "Test Taro"
        )) as Profile
        val user = Factory.make("User", mapOf(
                "profile" to profile
        )) as User
        val actual = Reservation.create(
                mapOf(
                        "user" to user,
                        "check_in_datetime" to "2021-01-01T15:00:00"
                )
        )
        assertThat(actual.getUserProfileName().toString()).isEqualTo("Test Taro")
    }

    @Test
    fun testCreateFactoryWithoutNecessaryParameters() {
        val profile = Factory.make("Profile", mapOf(
                "name" to "Test Taro"
        )) as Profile
        val user = Factory.make("User", mapOf(
                "profile" to profile
        )) as User
        try {
            Reservation.create(
                    mapOf(
                            "user" to user,
                    )
            )
            fail("??????????????????????????????????????????Exception???????????????????????????")
        } catch (e: InvalidParameterException) {
            assertThat(e.message).isEqualTo("check_in_datetime: ????????????????????????????????????")
        }
    }
}