package com.taurin.minpaku.util

import org.slf4j.LoggerFactory
import java.text.ParseException
import java.text.SimpleDateFormat
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

        fun getNextMonth(year: Int, month: Int): List<Int> {
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month - 1)
            cal.add(Calendar.MONTH, 1)
            val nextYear = cal.get(Calendar.YEAR)
            val nextMonth = cal.get(Calendar.MONTH) + 1

            return arrayListOf(nextYear, nextMonth)
        }

        fun getPrevMonth(year: Int, month: Int): List<Int> {
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month - 1)
            cal.add(Calendar.MONTH, -1)
            val prevYear = cal.get(Calendar.YEAR)
            val prevMonth = cal.get(Calendar.MONTH) + 1

            return arrayListOf(prevYear, prevMonth)
        }

        fun getDateFromStr(dateStr: String?): Date {
            if (dateStr == null) {
                return Date()
            }
            val sdFormat = SimpleDateFormat("yyyy-MM-dd")
            return sdFormat.let {
                try {
                    it.parse(dateStr)
                } catch (e: ParseException) {
                    Date()
                }
            }
        }

        /**
         * 指定された日付の間のDateをListで返す。
         * endDateStrで指定された日付は含まない。
         */
        fun getDateListFromDuration(startDateStr: String, endDateStr: String): List<Date> {
            val startDate = this.getDateFromStr(startDateStr)
            val endDate = this.getDateFromStr(endDateStr)
            val cal = Calendar.getInstance()
            cal.time = startDate
            val dateList = mutableListOf<Date>()
            while(cal.time.before(endDate)) {
                dateList.add(cal.time)
                cal.add(Calendar.DATE, 1)
            }

            return dateList
        }

        fun getNextDate(targetDate: Date): Date {
            val cal = Calendar.getInstance()
            cal.time = targetDate
            cal.add(Calendar.DATE, 1)
            return cal.time
        }

        fun getDateStr(date: Date): String {
            val df = SimpleDateFormat("yyyy-MM-dd")
            return df.format(date)
        }
    }
}