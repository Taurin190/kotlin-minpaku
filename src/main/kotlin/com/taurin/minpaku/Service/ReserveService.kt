package com.taurin.minpaku.Service

import com.taurin.minpaku.Entity.Reservation
import com.taurin.minpaku.Exception.DBException
import com.taurin.minpaku.Repository.ReserveRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.util.*

@Service
class ReserveService {
    @Autowired
    private lateinit var reserveRepository: ReserveRepository

    private val logger = LoggerFactory.getLogger(ReserveService::class.java)

    fun getReservationsByDuration(year: Int, month: Int): List<Reservation> {
        val cal = Calendar.getInstance()
        cal.set(year, month + 1, 1)
        cal.add(Calendar.DATE, -1)
        val lastDayOfMonth = cal.get(Calendar.DATE)
        return reserveRepository
            .findAllByDuration(
                Date.valueOf("$year-$month-1"),
                Date.valueOf("$year-$month-$lastDayOfMonth")
            )
    }

    @Transactional
    fun reserve(reservation: Reservation) {
        try {
            reserveRepository.save(reservation)
        } catch (e: Exception) {
            logger.warn("Reserve fail with Unexpected Exception: ${e.message}")
            throw DBException("登録出来ませんでした。")
        }
    }
}