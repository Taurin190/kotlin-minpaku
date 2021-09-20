package com.taurin.minpaku.infrastructure.datasource

import com.taurin.minpaku.domain.reservation.Reservation
import com.taurin.minpaku.domain.reservation.Reservations
import com.taurin.minpaku.infrastructure.Repository.ReserveRepository
import org.springframework.stereotype.Repository
import java.sql.Date
import java.time.LocalDate

@Repository
class ReserveDataSource(
    val reserveRepository: ReserveRepository
) : com.taurin.minpaku.domain.datasource.ReserveRepository {
    override fun findAllByDuration(
        startDate: LocalDate,
        endDate: LocalDate
    ): Reservations {

        val reservations = Reservations()
        val reservationList = reserveRepository.findAllByDuration(
            Date.valueOf(startDate),
            Date.valueOf(endDate)
        )

        reservationList.forEach {
            reservations.append(
                reservations.append(it.toDomain())
            )
        }
        return reservations
    }

    override fun register(reservation: Reservation): Reservation {
        TODO("Not yet implemented")
    }
}