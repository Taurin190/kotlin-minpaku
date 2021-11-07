package com.taurin.minpaku.unit.domain.user

import com.taurin.minpaku.domain.model.user.*
import com.taurin.minpaku.domain.type.Permission
import com.taurin.minpaku.helper.Factory
import com.taurin.minpaku.helper.ProfileFactory
import com.taurin.minpaku.helper.UserFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserTest {
    @BeforeEach
    fun setUp() {
        Factory.define("Profile", ProfileFactory.make())
        Factory.define("User", UserFactory.make())
    }

    @Test
    fun testHasProfileWithProfile() {
        val profile = Factory.make("Profile", null) as Profile
        val user = Factory.make("User", mapOf(
            "profile" to profile
        )) as User
        assertThat(user.hasProfile()).isTrue
    }

    @Test
    fun testHasProfileWithOutProfile() {
        val user = Factory.make("User", null) as User
        assertThat(user.hasProfile()).isFalse
    }

    @Test
    fun testIsAdminWithAdminUser() {
        val user = Factory.make("User", mapOf(
            "permission" to "ADMIN"
        )) as User
        assertThat(user.isAdmin()).isTrue
    }

    @Test
    fun testIsAdminWithNormalUser() {
        val user = Factory.make("User", mapOf(
            "permission" to "USER"
        )) as User
        assertThat(user.isAdmin()).isFalse
    }
}