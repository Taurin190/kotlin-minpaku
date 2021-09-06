package com.taurin.minpaku.unit.domain

import com.taurin.minpaku.domain.reservation.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDateTime

class ReservationTest {
    @Test
    fun testReservationJson() {
        val actual = Reservation(
            Title("Test Reservation"),
            CheckInDateTime(LocalDateTime.parse("2021-01-01T15:00:00")),
            CheckOutDateTime(LocalDateTime.parse("2021-01-03T10:00:00")),
            Url("http://localhost/test")
        )

        assertThat(actual.toJson())
            .isEqualTo("{\"title\": \"Test Reservation\"," +
                    "\"start\": \"2021-01-01\"," +
                    "\"end\": \"2021-01-03\"," +
                    "\"url\": \"http://localhost/test\"}")
    }

    @Test
    fun testReservationString() {
        val actual = Reservation(
            Title("Test Reservation"),
            CheckInDateTime(LocalDateTime.parse("2021-01-01T15:00:00")),
            CheckOutDateTime(LocalDateTime.parse("2021-01-03T10:00:00")),
            Url("http://localhost/test")
        )

        assertThat(actual.toString())
            .isEqualTo("Reservation [title=Test Reservation, " +
                    "start=2021-01-01, " +
                    "end=2021-01-03, " +
                    "url=http://localhost/test]")
    }

    @Test
    fun testCheckInDateTimeWithOlderThanValid() {
        try {
            Reservation(
                Title("Test Reservation"),
                CheckInDateTime(LocalDateTime.parse("1999-12-31T15:00:00")),
                CheckOutDateTime(LocalDateTime.parse("2000-01-01T10:00:00")),
                Url("http://localhost/test")
            )
            fail(AssertionError("想定される例外が発生しませんでした。"))
        } catch (e: Exception) {
            assertThat(e.message).isIn("指定された日付正しい範囲にありません。")
        }
    }

    @Test
    fun testCheckOutDateTimeWithNewerThanValid() {
        try {
            Reservation(
                Title("Test Reservation"),
                CheckInDateTime(LocalDateTime.parse("2040-12-31T15:00:00")),
                CheckOutDateTime(LocalDateTime.parse("2041-01-01T10:00:00")),
                Url("http://localhost/test")
            )
            fail(AssertionError("想定される例外が発生しませんでした。"))
        } catch (e: Exception) {
            assertThat(e.message).isIn("指定された日付正しい範囲にありません。")
        }
    }

    @Test
    fun testEarlierCheckOutDateThanCheckIn() {
        try {
            Reservation(
                Title("Test Reservation"),
                CheckInDateTime(LocalDateTime.parse("2021-10-01T15:00:00")),
                CheckOutDateTime(LocalDateTime.parse("2021-09-30T10:00:00")),
                Url("http://localhost/test")
            )
            fail(AssertionError("想定される例外が発生しませんでした。"))
        } catch (e: Exception) {
            assertThat(e.message).isIn("指定された日付正しい範囲にありません。")
        }
    }
}