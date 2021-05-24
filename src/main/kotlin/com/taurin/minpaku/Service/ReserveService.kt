package com.taurin.minpaku.Service

import com.taurin.minpaku.Entity.Reservation
import com.taurin.minpaku.Repository.ReserveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date
import java.util.*

@Service
class ReserveService {
    @Autowired
    private lateinit var reserveRepository: ReserveRepository

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
}