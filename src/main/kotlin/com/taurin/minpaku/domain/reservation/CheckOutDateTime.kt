package com.taurin.minpaku.domain.reservation

import java.time.LocalDateTime

/*
 * 予約したチェックアウト日時
 */
class CheckOutDateTime(
    override val value: LocalDateTime
) : ReservationDateTime(value) {
}