package com.taurin.minpaku.unit.domain.user

import com.taurin.minpaku.domain.model.user.*
import com.taurin.minpaku.domain.type.Permission
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserTest {
    @Test
    fun testHasProfileWithProfile() {
        val profile = Profile(
            Name("Test太郎"),
            EmailAddress("test@gmail.com"),
            PhoneNumber("080-1234-5678")
        )
        val user = User(
            UserName("testtaro"),
            profile,
            Permission.USER
        )

        assertThat(user.hasProfile()).isTrue
    }

    @Test
    fun testHasProfileWithOutProfile() {
        val user = User(
            UserName("testtaro"),
            null,
            Permission.USER
        )

        assertThat(user.hasProfile()).isFalse
    }

    @Test
    fun testIsAdminWithAdminUser() {
        val user = User(
            UserName("testtaro"),
            null,
            Permission.ADMIN
        )

        assertThat(user.isAdmin()).isTrue
    }

    @Test
    fun testIsAdminWithNormalUser() {
        val user = User(
            UserName("testtaro"),
            null,
            Permission.USER
        )

        assertThat(user.isAdmin()).isFalse
    }
}