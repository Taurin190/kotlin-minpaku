package com.taurin.minpaku.helper

import com.taurin.minpaku.domain.model.reservation.*
import com.taurin.minpaku.domain.model.user.*
import com.taurin.minpaku.domain.type.Permission
import java.time.LocalDateTime



class ReservationFactory {
    companion object {
        fun make(): (parameters :Map<String, Any>?) -> Reservation {
            return fun (parameters :Map<String, Any>?): Reservation {
                return Reservation(
                    Title(parameters?.get("title") as String? ?: "Test Reservation"),
                    CheckInDateTime(LocalDateTime.parse(parameters?.get("check_in_datetime") as String? ?: "2021-01-01T15:00:00")),
                    if (parameters?.get("check_out_datetime") != null) {
                        CheckOutDateTime(LocalDateTime.parse(parameters["check_out_datetime"] as String))
                    } else null,
                    Url(parameters?.get("url") as String? ?: "http://localhost/test"),
                    if (parameters?.get("user") != null) parameters["user"] as User else null
                )
            }
        }
    }
}