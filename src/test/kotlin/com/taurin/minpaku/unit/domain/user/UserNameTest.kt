package com.taurin.minpaku.unit.domain.user

import com.taurin.minpaku.domain.model.user.UserName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserNameTest {
    @Test
    fun testUserNameWithValidName() {
        val actual = UserName("test.taro123")
        assertThat(actual.toString()).isEqualTo("test.taro123")
    }

    @Test
    fun testUserNameWithInvalidShortName() {
        try {
            UserName("test")
        } catch(e: IllegalStateException) {
            assertThat(e.message).isEqualTo("UserNameは5文字以上、20文字以内の英数字および._で、先頭は英数字を設定してください。")
        }
    }

    @Test
    fun testUserNameWithInvalidLongName() {
        try {
            UserName("test.test.test.test.t")
        } catch(e: IllegalStateException) {
            assertThat(e.message).isEqualTo("UserNameは5文字以上、20文字以内の英数字および._で、先頭は英数字を設定してください。")
        }
    }

    @Test
    fun testUserNameWithInvalidCharName() {
        try {
            UserName("test.太郎")
        } catch(e: IllegalStateException) {
            assertThat(e.message).isEqualTo("UserNameは5文字以上、20文字以内の英数字および._で、先頭は英数字を設定してください。")
        }
    }
}