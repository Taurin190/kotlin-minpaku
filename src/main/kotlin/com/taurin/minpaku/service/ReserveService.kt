package com.taurin.minpaku.service

import com.taurin.minpaku.domain.model.reservation.Reservations
import com.taurin.minpaku.domain.model.reservation.Reservation as ReservationDomain
import com.taurin.minpaku.infrastructure.Entity.Reservation
import com.taurin.minpaku.infrastructure.Repository.ReserveRepository
import com.taurin.minpaku.infrastructure.datasource.ReserveDataSource
import com.taurin.minpaku.infrastructure.exception.DBException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
class ReserveService {
    @Autowired
    private lateinit var reserveRepository: ReserveRepository

    @Autowired
    private lateinit var reserveDataSource: ReserveDataSource

    private val logger = LoggerFactory.getLogger(ReserveService::class.java)

    fun getReservationsInMonth(year: Int, month: Int): Reservations {
        val cal = Calendar.getInstance()
        cal.set(year, month, 1)
        cal.add(Calendar.DATE, -1)
        val lastDayOfMonth = cal.get(Calendar.DATE)
        val padMonth = "$month".padStart(2, '0')
        return reserveDataSource.findAllByDuration(
            LocalDate.parse("$year-$padMonth-01"),
            LocalDate.parse("$year-$padMonth-$lastDayOfMonth")
        )
    }

    @Transactional
    fun reserve(reservation: Reservation) {
        try {
            reserveRepository.save(reservation)
        } catch (e: Exception) {
            logger.warn("Reserve fail with Exception: ${e.message}")
            throw DBException("登録出来ませんでした。")
        }
    }

    @Transactional
    fun register(reservation: ReservationDomain) {
        try {
            reserveDataSource.register(reservation)
        } catch (e: Exception) {
            logger.warn("Reserve fail with Exception: ${e.message}")
            throw DBException("登録出来ませんでした。: ${e.message}")
        }
    }
}