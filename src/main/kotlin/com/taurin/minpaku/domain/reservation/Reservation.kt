package com.taurin.minpaku.domain.reservation

import com.taurin.minpaku.infrastructure.Entity.Book
import java.time.LocalDateTime
import com.taurin.minpaku.infrastructure.Entity.Reservation as ReservationEntity

class Reservation(
    var _title: Title,
    var _checkInDateTime: CheckInDateTime,
    var _checkOutDateTime: CheckOutDateTime?,
    var _url: Url?
) {
    private val title: Title
    private val checkInDateTime: CheckInDateTime
    private val checkOutDateTime: CheckOutDateTime?
    private val url: Url?

    init {
        title = _title
        checkInDateTime = _checkInDateTime
        checkOutDateTime = _checkOutDateTime
        verifyCheckInOutDateTime(checkInDateTime, checkOutDateTime)
        url = _url
    }

    fun toJson(): String {
        val sb = StringBuilder()
        sb.append("{\"title\": \"$title\"")
        sb.append(",\"start\": \"$checkInDateTime\"")
        if (checkOutDateTime != null) {
            sb.append(",\"end\": \"$checkOutDateTime\"")
        }
        if (url != null) {
            sb.append(",\"url\": \"$url\"")
        }
        sb.append("}")
        return sb.toString()
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Reservation [title=$title")
        sb.append(", start=$checkInDateTime")
        if (checkOutDateTime != null) {
            sb.append(", end=$checkOutDateTime")
        }
        if (url != null) {
            sb.append(", url=$url")
        }
        sb.append("]")
        return sb.toString()
    }

    private fun verifyCheckInOutDateTime(checkInDateTime: CheckInDateTime, checkOutDateTime: CheckOutDateTime?) {
        if (!checkInDateTime.validateDate()) {
            throw IllegalStateException("指定された日付正しい範囲にありません。")
        }
        if (checkOutDateTime == null) {
            return
        }
        if (!checkOutDateTime.validateDate()) {
            throw IllegalStateException("指定された日付正しい範囲にありません。")
        }

        if (!checkInDateTime.isEarlierThan(checkOutDateTime)) {
            throw IllegalStateException("指定された日付正しい範囲にありません。")
        }

        if (checkInDateTime.getDaysOfStay(checkOutDateTime) > 3) {
            throw IllegalStateException("指定された日付正しい範囲にありません。")
        }
    }

    companion object {
        fun fromEntity(reservation: ReservationEntity) =
            Reservation(
                Title(reservation.user?.userName ?: "Guest"),
                CheckInDateTime(
                    reservation.checkInDateTime
                ),
                CheckOutDateTime(
                    reservation.checkOutDateTime
                ),
                null
            )

        fun toEntity(reservation: Reservation): ReservationEntity {
            // TODO checkOutDateTimeでnullableにしている部分を修正する。
            return ReservationEntity(
                null,
                null,
                mutableListOf<Book>(),
                reservation.checkInDateTime.value,
                reservation.checkOutDateTime?.value ?: LocalDateTime.parse("2000-01-02T10:00:00"),
            )
        }
    }
}