package com.taurin.minpaku.domain.model.reservation

import java.time.LocalDateTime

/*
 * 予約したチェックアウト日時
 */
class CheckOutDateTime(
    override val value: LocalDateTime
) : ReservationDateTime(value) {
    override fun validateDate(): Boolean {
        // 2000 ~ 2040年までを正常な値とする
        if (value.year !in 2000..2040) {
            return false
        }
        if (value.hour !in 6..11) {
            return false
        }
        return true
    }
}