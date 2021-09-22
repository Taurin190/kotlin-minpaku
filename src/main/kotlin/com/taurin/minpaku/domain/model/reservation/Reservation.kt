package com.taurin.minpaku.domain.model.reservation

//TODO APIのレスポンスの出し方に引きずられているため分離する
class Reservation(
    var _title: Title,
    var _checkInDateTime: CheckInDateTime,
    var _checkOutDateTime: CheckOutDateTime?,
    var _url: Url?
) {
    private val title: Title = _title
    val checkInDateTime: CheckInDateTime = _checkInDateTime
    val checkOutDateTime: CheckOutDateTime = _checkOutDateTime ?: checkInDateTime.getDefaultCheckOutDateTime()
    private val url: Url?

    init {
        verifyCheckInOutDateTime(checkInDateTime, checkOutDateTime)
        url = _url
    }

    fun toJson(): String {
        val sb = StringBuilder()
        sb.append("{\"title\": \"$title\"")
        sb.append(",\"start\": \"$checkInDateTime\"")
        sb.append(",\"end\": \"$checkOutDateTime\"")

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
        sb.append(", end=$checkOutDateTime")

        if (url != null) {
            sb.append(", url=$url")
        }
        sb.append("]")
        return sb.toString()
    }

    private fun verifyCheckInOutDateTime(checkInDateTime: CheckInDateTime, checkOutDateTime: CheckOutDateTime) {
        if (!checkInDateTime.isEarlierThan(checkOutDateTime)) {
            throw IllegalStateException("指定された日付正しい範囲にありません。")
        }

        if (checkInDateTime.getDaysOfStay(checkOutDateTime) > 3) {
            throw IllegalStateException("指定された日付正しい範囲にありません。")
        }
    }
}