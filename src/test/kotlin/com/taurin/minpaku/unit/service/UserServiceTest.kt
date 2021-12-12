package com.taurin.minpaku.unit.service

import com.taurin.minpaku.domain.model.user.User
import com.taurin.minpaku.helper.Factory
import com.taurin.minpaku.helper.UserFactory
import com.taurin.minpaku.infrastructure.datasource.UserDataSource
import com.taurin.minpaku.service.UserService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserServiceTest {
    @MockK
    private lateinit var userDataSource: UserDataSource

    @InjectMockKs
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        Factory.define("User", UserFactory.make())
    }

    @Test
    fun testGetByUserName() {
        val user = Factory.make("User", mapOf(
                "user_name" to "test123"
        )) as User
        every {
            userDataSource.findByUserName(any())
        } returns user
        val actual = userDataSource.findByUserName("test123")
        assertThat(actual.userName.toString()).isEqualTo("test123")
        assertThat(actual.isAdmin()).isFalse
        assertThat(actual.hasProfile()).isFalse
    }
}