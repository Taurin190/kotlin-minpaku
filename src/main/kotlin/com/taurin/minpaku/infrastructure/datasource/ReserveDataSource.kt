package com.taurin.minpaku.infrastructure.datasource

import com.taurin.minpaku.domain.model.reservation.Reservation
import com.taurin.minpaku.domain.model.reservation.Reservations
import com.taurin.minpaku.domain.model.user.User
import com.taurin.minpaku.infrastructure.Entity.Reservation as ReservationEntity
import com.taurin.minpaku.infrastructure.Repository.ReserveRepository
import com.taurin.minpaku.infrastructure.Repository.UserRepository
import com.taurin.minpaku.service.ReserveService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.sql.Date
import java.time.LocalDate

@Repository
class ReserveDataSource(
    val reserveRepository: ReserveRepository,
    val userRepository: UserRepository
) : com.taurin.minpaku.domain.datasource.ReserveRepository {
    private val logger = LoggerFactory.getLogger(ReserveDataSource::class.java)

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

    override fun register(reservation: Reservation) {
        val user = userRepository.findByUserName(reservation.getUser().userName.toString())
        val reservationEntity = ReservationEntity.fromDomain(reservation, user)
        logger.info(reservationEntity.toString())
        reserveRepository.save(reservationEntity)
    }
}