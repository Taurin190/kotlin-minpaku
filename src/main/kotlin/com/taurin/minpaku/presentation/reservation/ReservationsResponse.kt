package com.taurin.minpaku.presentation.reservation

import com.taurin.minpaku.domain.model.reservation.Reservation
import com.taurin.minpaku.domain.model.reservation.Reservations

class ReservationsResponse(val reservations: Reservations) {
    val json: String

    init {
        json = convertReservationsToJson(reservations)
    }

    private fun convertReservationsToJson(reservations: Reservations): String {
        val sb = StringBuilder()
        sb.append("[")
        reservations.forEach {
            sb.append(convertReservationToJson(it))
            sb.append(",")
        }
        if (sb.length > 1) {
            sb.setLength(sb.length - 1)
        }
        sb.append("]")
        return sb.toString()
    }

    private fun convertReservationToJson(reservation: Reservation): String {
        val sb = StringBuilder()
        sb.append("{\"title\": \"${reservation.getUserProfileName()}\"")
        sb.append(",\"start\": \"${reservation.getCheckInDateTime()}\"")
        sb.append(",\"end\": \"${reservation.getCheckOutDateTime()}\"")

        if (reservation.getUrl() != null) {
            sb.append(",\"url\": \"${reservation.getUrl()}\"")
        }
        sb.append("}")
        return sb.toString()
    }
}