package com.taurin.minpaku.domain.reservation

class Reservations {
    val reservationList = mutableListOf<Reservation>()

    fun append(reservation: Reservation) {
        reservationList.add(reservation)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Reservations" + toJson())
        return sb.toString()
    }

    fun toJson(): String {
        val sb = StringBuilder()
        sb.append("[")
        reservationList.forEach {
            sb.append(it.toJson())
            sb.append(",")
        }
        if (sb.length > 1) {
            sb.setLength(sb.length - 1)
        }
        sb.append("]")
        return sb.toString()
    }

    fun count() = reservationList.count()
    fun append(reservation: Unit) {

    }
}