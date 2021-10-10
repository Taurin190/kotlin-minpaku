package com.taurin.minpaku.presentation.reservation

import com.taurin.minpaku.domain.model.reservation.Reservation
import com.taurin.minpaku.domain.model.reservation.Reservations

class ReservationIterator(val reservations: Reservations): Iterator<Reservation> {
    private var index = 0

    override fun hasNext(): Boolean {
        return reservations.has(index)
    }

    override fun next(): Reservation {
        index += 1
        return reservations.get(index - 1)
    }
}