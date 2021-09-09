package com.taurin.minpaku.infrastructure.Repository

import com.taurin.minpaku.infrastructure.Entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReserveRepository : JpaRepository<Reservation, Long> {
    @Modifying
    @Query(
        value = "SELECT r.reservation_id, b.book_id, b.guest_num, b.stay_date, r.created_datetime, r.updated_datetime, r.user_id " +
                "FROM reservation r " +
                "JOIN book b ON r.reservation_id = b.reservation_id " +
                "WHERE b.stay_date BETWEEN :start_date AND :end_date " +
                "ORDER BY b.stay_date ASC;", nativeQuery = true)
    fun findAllByDuration(
        @Param("start_date") startDate: Date,
        @Param("end_date") endDate: Date
    ): List<Reservation>
}