package com.taurin.minpaku.domain.reservation

class Reservation(
    var title: Title,
    var checkInDateTime: CheckInDateTime,
    var checkOutDateTime: CheckOutDateTime?,
    var url: Url?
) {

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