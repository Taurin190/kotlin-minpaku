package com.taurin.minpaku.domain.model.reservation

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/*
 * 予約した宿泊開始日
 */
class CheckInDateTime(
    override val value: LocalDateTime
) : ReservationDateTime(value) {
    init {
        if (!validateDate()) {
            throw IllegalStateException("指定された日付正しい範囲にありません。")
        }
    }

    fun isEarlierThan(checkOutDateTime: CheckOutDateTime) = value < checkOutDateTime.value

    /**
     * LocalDateTimeをLocalDateに変換して宿泊日数を返す
     */
    fun getDaysOfStay(checkOutDateTime: CheckOutDateTime): Long {
        val checkOutDate = checkOutDateTime.value.toLocalDate()
        val checkInDate = value.toLocalDate()

        return ChronoUnit.DAYS.between(checkInDate, checkOutDate)
    }

    fun getDefaultCheckOutDateTime(): CheckOutDateTime {
        val nextDate = value.plusDays(1)
        return CheckOutDateTime(
            nextDate
                .withHour(10)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
        )

    }

    override fun validateDate(): Boolean {
        // 2000 ~ 2040年までを正常な値とする
        if (value.year !in 2000..2040) {
            return false
        }
        if (value.hour !in 15..23) {
            return false
        }
        return true
    }
}