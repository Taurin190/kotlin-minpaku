package com.taurin.minpaku.domain.reservation

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/*
 * 予約した宿泊開始日
 */
class CheckInDateTime(
    val value: LocalDateTime
) : java.io.Serializable {
    override fun toString() = value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}