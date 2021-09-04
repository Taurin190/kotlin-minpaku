package com.taurin.minpaku.domain.reservation

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/*
 * 予約したチェックアウト日時
 */
class CheckOutDateTime(
    val value: LocalDateTime
) : java.io.Serializable {
    override fun toString() = value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}