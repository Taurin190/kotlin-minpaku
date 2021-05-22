package com.taurin.minpaku.Repository

import com.taurin.minpaku.Entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReserveRepository : JpaRepository<Reservation, Long> {
    fun findAllByDuration(start: Date, end: Date): List<Reservation>
}