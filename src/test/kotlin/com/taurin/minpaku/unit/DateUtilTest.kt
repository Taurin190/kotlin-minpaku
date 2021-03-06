package com.taurin.minpaku.unit

import com.taurin.minpaku.util.DateUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.*

class DateUtilTest {
    @Test
    fun testGetValidMonth() {
        val actual = DateUtil.getValidMonth("2")
        assertThat(actual).isEqualTo(2)
    }

    @Test
    fun testGetValidMonthWithInvalidValue() {
        val cal = Calendar.getInstance()
        val currentMonth = cal.get(Calendar.MONTH) + 1
        val actual = DateUtil.getValidMonth("0")
        assertThat(actual).isEqualTo(currentMonth)
    }

    @Test
    fun testGetValidMonthWithInvalidInput() {
        val cal = Calendar.getInstance()
        val currentMonth = cal.get(Calendar.MONTH) + 1
        val actual = DateUtil.getValidMonth("aaa")
        assertThat(actual).isEqualTo(currentMonth)
    }

    @Test
    fun testGetValidYear() {
        val actual = DateUtil.getValidYear("2020")
        assertThat(actual).isEqualTo(2020)
    }

    @Test
    fun testGetValidYearLowerBoundaryWithValidValue() {
        val actual = DateUtil.getValidYear("2000")
        assertThat(actual).isEqualTo(2000)
    }

    @Test
    fun testGetValidYearLowerBoundaryWithInValidValue() {
        val cal = Calendar.getInstance()
        val actual = DateUtil.getValidYear("1999")
        val currentYear = cal.get(Calendar.YEAR)
        assertThat(actual).isEqualTo(currentYear)
    }

    @Test
    fun testGetValidYearHigherBoundaryWithValidValue() {
        val actual = DateUtil.getValidYear("2040")
        assertThat(actual).isEqualTo(2040)
    }

    @Test
    fun testGetValidYearHigherBoundaryWithInValidValue() {
        val cal = Calendar.getInstance()
        val actual = DateUtil.getValidYear("2041")
        val currentYear = cal.get(Calendar.YEAR)
        assertThat(actual).isEqualTo(currentYear)
    }

    @Test
    fun testGetValidYearWithInValidValue() {
        val cal = Calendar.getInstance()
        val actual = DateUtil.getValidYear("aaa")
        val currentYear = cal.get(Calendar.YEAR)
        assertThat(actual).isEqualTo(currentYear)
    }

    @Test
    fun testGetDateFromStr() {
        val actual = DateUtil.getDateFromStr("2020-01-01")
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        assertThat(sdf.format(actual)).isEqualTo("2020/01/01")
    }

    @Test
    fun testGetDateFromStrWithInvalidFormat() {
        val actual = DateUtil.getDateFromStr("AAAABBBBCC")
        val cal = Calendar.getInstance()
        val currentYear = cal.get(Calendar.YEAR)
        val currentMonth = cal.get(Calendar.MONTH)
        val currentDate = cal.get(Calendar.DATE)

        cal.time = actual
        assertThat(cal.get(Calendar.YEAR)).isEqualTo(currentYear)
        assertThat(cal.get(Calendar.MONTH)).isEqualTo(currentMonth)
        assertThat(cal.get(Calendar.DATE)).isEqualTo(currentDate)
    }

    @Test
    fun testGetDateFromStrWithoutStr() {
        val cal = Calendar.getInstance()
        val actual = DateUtil.getDateFromStr(null)
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val currentYear = cal.get(Calendar.YEAR)
        val currentMonth = cal.get(Calendar.MONTH) + 1
        val currentDate = cal.get(Calendar.DATE)
        val currentMonthStr = "$currentMonth".padStart(2, '0')
        val currentDateStr = "$currentDate".padStart(2, '0')

        assertThat(sdf.format(actual)).isEqualTo("$currentYear/$currentMonthStr/$currentDateStr")
    }

    @Test
    fun testGetNextDate() {
        val cal = Calendar.getInstance()
        cal.set(2021, 0, 1)
        val actual = DateUtil.getNextDate(cal.time)
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        assertThat(sdf.format(actual)).isEqualTo("2021/01/02")
    }

    @Test
    fun testGetDateStr() {
        val cal = Calendar.getInstance()
        cal.set(2021, 2, 1)
        val actual = DateUtil.getDateStr(cal.time)
        assertThat(actual).isEqualTo("2021-03-01")
    }

    @Test
    fun testGetDateListFromDuration() {
        val actual = DateUtil.getDateListFromDuration("2021-01-01", "2021-01-10")
        assertThat(actual.size).isEqualTo(9)
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        assertThat(sdf.format(actual[0])).isEqualTo("2021/01/01")
        assertThat(sdf.format(actual.last())).isEqualTo("2021/01/09")
    }

    @Test
    fun testGetDateListFromDurationWithoutDuration() {
        val actual = DateUtil.getDateListFromDuration("2021-01-01", "2021-01-01")
        assertThat(actual.size).isEqualTo(0)
    }

    @Test
    fun testGetDateListFromDurationWithInvalidDuration() {
        val actual = DateUtil.getDateListFromDuration("2021-02-01", "2021-01-01")
        assertThat(actual.size).isEqualTo(0)
    }
}