package com.taurin.minpaku.Repository

import com.taurin.minpaku.Entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.sql.Date

@Repository
interface ReserveRepository : JpaRepository<Reservation, Long> {
    @Modifying
    @Query(
        value = "SELECT * FROM reservation r " +
                "JOIN book b ON r.reservation_id = b.reservation_id " +
                "WHERE b.stay_date BETWEEN :start AND :end;", nativeQuery = true)
    fun findAllByDuration(
        @Param("start") start: Date,
        @Param("end") end: Date
    ): List<Reservation>
}