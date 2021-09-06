package com.taurin.minpaku.domain.reservation

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/*
 * 予約した宿泊開始日
 */
class CheckInDateTime(
    override val value: LocalDateTime
) : ReservationDateTime(value) {
    fun isEarlierThan(checkOutDateTime: CheckOutDateTime) = value < checkOutDateTime.value
}