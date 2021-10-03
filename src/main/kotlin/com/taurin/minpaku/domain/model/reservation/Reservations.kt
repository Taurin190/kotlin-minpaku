package com.taurin.minpaku.domain.model.reservation

import com.taurin.minpaku.presentation.reservation.ReservationIterator

class Reservations: Iterable<Reservation> {
    val reservationList = mutableListOf<Reservation>()

    private var iterator = 0

    fun append(reservation: Reservation) {
        reservationList.add(reservation)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Reservations" + toJson())
        return sb.toString()
    }

    fun has(index: Int) = index < count()

    fun get(index: Int) = reservationList[index]

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

    override fun iterator(): Iterator<Reservation> {
        return ReservationIterator(this)
    }

}