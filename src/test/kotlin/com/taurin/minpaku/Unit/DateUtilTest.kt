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
}