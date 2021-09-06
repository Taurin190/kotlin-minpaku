package com.taurin.minpaku.infrastructure.datasource

import com.taurin.minpaku.domain.reservation.Reservations
import com.taurin.minpaku.infrastructure.Repository.ReserveRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ReserveDataSource(
    val reserveRepository: ReserveRepository
) {
    fun findAllByDuration(
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime
    ): Reservations {
        return Reservations()
    }
}