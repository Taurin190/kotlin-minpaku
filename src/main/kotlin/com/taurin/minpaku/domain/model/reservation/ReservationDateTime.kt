package com.taurin.minpaku.domain.model.reservation

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


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

    fun getDate(): Date {
        val zone = ZoneId.systemDefault()
        val zonedDateTime = ZonedDateTime.of(value, zone)
        val instant = zonedDateTime.toInstant();
        return Date.from(instant)
    }
}