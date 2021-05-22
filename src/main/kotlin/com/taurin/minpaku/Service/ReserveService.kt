package com.taurin.minpaku.Service

import com.taurin.minpaku.Entity.Reservation
import com.taurin.minpaku.Repository.ReserveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReserveService {
    @Autowired
    private lateinit var reserveRepository: ReserveRepository

    fun getReservationsByDuration(year: Int, month: Int) : List<Reservation> {
        lateinit var reservations: List<Reservation>
        reservations = reserveRepository
            .findAllByDuration(
                Date(year, month, 1),
                Date(year, month, 31)
            )
        return reservations
    }
}