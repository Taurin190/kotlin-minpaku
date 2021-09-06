package com.taurin.minpaku.domain.reservation

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class ReservationDateTime(
    open val value: LocalDateTime
) {
    override fun toString(): String = value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    open fun validateDate(): Boolean {
        // 2000 ~ 2040年までを正常な値とする
        if (value.year !in 2000..2040) {
            return false
        }
        return true
    }
}