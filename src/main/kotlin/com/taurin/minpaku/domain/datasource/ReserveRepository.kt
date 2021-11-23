package com.taurin.minpaku.domain.datasource

import com.taurin.minpaku.domain.model.reservation.Reservation
import com.taurin.minpaku.domain.model.reservation.Reservations
import java.time.LocalDate

interface ReserveRepository {
    fun findAllByDuration(
        startDate: LocalDate,
        endDate: LocalDate
    ): Reservations

    fun register(reservation: Reservation)
}