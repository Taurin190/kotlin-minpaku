package com.taurin.minpaku.unit.domain.user

import com.taurin.minpaku.domain.model.user.PhoneNumber
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.jupiter.api.Test

class PhoneNumberTest {
    @Test
    fun testPhoneNumberWithValidNumber() {
        val actual = PhoneNumber("080-1234-5678")
        assertThat(actual.toString()).isEqualTo("080-1234-5678")
    }

    @Test
    fun testPhoneNumberWithInvalidNumber() {
        try {
            PhoneNumber("08012345")
            fail("不正な値に例外が発生しませんでした。")
        } catch (e: IllegalStateException) {
            assertThat(e.message).isEqualTo("電話番号は12文字以上、13文字以内の数字および-で、先頭は0を設定してください。")
        }
    }

    @Test
    fun testPhoneNumberWithInvalidLongNumber() {
        try {
            PhoneNumber("080-1234-567890")
            fail("不正な値に例外が発生しませんでした。")
        } catch (e: IllegalStateException) {
            assertThat(e.message).isEqualTo("電話番号は12文字以上、13文字以内の数字および-で、先頭は0を設定してください。")
        }
    }

    @Test
    fun testPhoneNumberWithInvalidCharNumber() {
        try {
            PhoneNumber("080-aa34-5678")
            fail("不正な値に例外が発生しませんでした。")
        } catch (e: IllegalStateException) {
            assertThat(e.message).isEqualTo("電話番号は12文字以上、13文字以内の数字および-で、先頭は0を設定してください。")
        }
    }
}