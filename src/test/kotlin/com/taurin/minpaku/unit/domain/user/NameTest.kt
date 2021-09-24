package com.taurin.minpaku.unit.domain.user

import com.taurin.minpaku.domain.model.user.Name
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.jupiter.api.Test

class NameTest {
    @Test
    fun testNameWithValidName() {
        val actual = Name("テスト　太郎")
        assertThat(actual.toString()).isEqualTo("テスト　太郎")
    }

    @Test
    fun testNameWithInvalidShortName() {
        try {
            val actual = Name("")
            fail("不正な値に例外が発生しませんでした。")
        } catch (e: IllegalStateException) {
            assertThat(e.message).isEqualTo("名前は1文字以上、20文字以内で指定して下さい。")
        }
    }

    @Test
    fun testNameWithInvalidLongName() {
        try {
            val actual = Name("aiueo kakikukeko sashisuseso")
            fail("不正な値に例外が発生しませんでした。")
        } catch (e: IllegalStateException) {
            assertThat(e.message).isEqualTo("名前は1文字以上、20文字以内で指定して下さい。")
        }
    }
}