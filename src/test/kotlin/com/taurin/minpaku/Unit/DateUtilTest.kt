package com.taurin.minpaku.Unit

import com.taurin.minpaku.Util.DateUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["application.runner.enabled=false"]
)
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
}