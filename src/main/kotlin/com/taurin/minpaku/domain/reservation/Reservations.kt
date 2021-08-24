package com.taurin.minpaku.domain.reservation

class Reservations(
    val reservationList: List<Reservation>)
{
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Reservations" + toJson())
        return sb.toString()
    }

    fun toJson(): String {
        val sb = StringBuilder()
        sb.append("[")
        reservationList.forEach{
            sb.append(it.toJson())
            sb.append(",")
        }
        sb.append("]")
        return sb.toString()
    }
}