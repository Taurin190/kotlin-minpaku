package com.taurin.minpaku.presentation.reservation

import com.taurin.minpaku.domain.model.reservation.Reservations

class ReservationsResponse(val reservations: Reservations) {
    val json: String

    init {
        json = reservations.toJson()
    }
}