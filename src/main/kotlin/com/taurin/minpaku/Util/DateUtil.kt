package com.taurin.minpaku.Util

import org.slf4j.LoggerFactory
import java.util.*

class DateUtil {

    companion object {
        private val logger = LoggerFactory.getLogger(DateUtil::class.java)

        fun getValidMonth(monthStr: String): Int {
            val month = monthStr.toIntOrNull()
            if (month != null) {
                if (1 <= month && month <= 12) {
                    return month
                }
                logger.warn("月の入力に不正な値: $month が入力されました。")
            } else {
                logger.warn("月の入力に数値以外の不正な値が入力されました。")
            }
            // 値が設定されていない、不正な値の場合は現在の月を返す
            val cal = Calendar.getInstance()
            // Monthは0から始まるため+1する。
            return cal.get(Calendar.MONTH) + 1
        }

        fun getValidYear(yearStr: String): Int {
            val year = yearStr.toIntOrNull()
            if (year != null) {
                // 一旦2040までに設定
                if (2000 <= year && year <= 2040) {
                    return year
                }
                logger.warn("年の入力に不正な値: $year が入力されました。")
            } else {
                logger.warn("年の入力に数値以外の不正な値が入力されました。")
            }
            // 値が設定されていない、不正な値の場合は現在の月を返す
            val cal = Calendar.getInstance()
            return cal.get(Calendar.YEAR)
        }
    }
}