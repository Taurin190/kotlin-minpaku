package com.taurin.minpaku.domain.reservation

import java.lang.IllegalStateException

class Reservation(
    var _title: Title,
    var _checkInDateTime: CheckInDateTime,
    var _checkOutDateTime: CheckOutDateTime?,
    var _url: Url?
) {
    val title: Title
    val checkInDateTime: CheckInDateTime
    val checkOutDateTime: CheckOutDateTime?
    val url: Url?

    init {
        title = _title
        checkInDateTime = _checkInDateTime
        checkOutDateTime = _checkOutDateTime
        checkInOutDate(checkInDateTime, checkOutDateTime)
        url = _url
    }

    fun checkInOutDate(checkInDateTime: CheckInDateTime, checkOutDateTime: CheckOutDateTime?) {
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
}