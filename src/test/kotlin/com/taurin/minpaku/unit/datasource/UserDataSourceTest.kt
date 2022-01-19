package com.taurin.minpaku.unit.datasource

import com.taurin.minpaku.domain.model.user.Profile
import com.taurin.minpaku.domain.model.user.User
import com.taurin.minpaku.domain.type.Permission
import com.taurin.minpaku.helper.Factory
import com.taurin.minpaku.helper.ProfileFactory
import com.taurin.minpaku.helper.UserFactory
import com.taurin.minpaku.infrastructure.Entity.User as UserEntity
import com.taurin.minpaku.infrastructure.Repository.UserRepository
import com.taurin.minpaku.infrastructure.datasource.UserDataSource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserDataSourceTest {
    @MockK
    private lateinit var userRepository: UserRepository

    @InjectMockKs
    private lateinit var userDataSource: UserDataSource

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        Factory.define("Profile", ProfileFactory.make())
        Factory.define("User", UserFactory.make())
    }

    @Test
    fun testFindByUsername() {
        val userEntity = UserEntity(
                1,
                "test123",
                "password123",
                Permission.USER
        )

        every {
            userRepository.findByUserName("test123")
        } returns userEntity

        val actual = userDataSource.findByUserName("test123")

        assertThat(actual.userName.toString()).isEqualTo("test123")
        assertThat(actual.hasProfile()).isFalse
        assertThat(actual.isAdmin()).isFalse
    }

    @Test
    fun testRegisterProfile() {
        val userEntity = UserEntity(
                1,
                "test123",
                "password123",
                Permission.USER
        )

        val profile = Factory.make("Profile", mapOf(
                "name" to "Test Taro"
        )) as Profile
        val user = Factory.make("User", mapOf(
                "user_name" to "test123",
                "profile" to profile
        )) as User

        every {
            userRepository.findByUserName("test123")
        } returns userEntity

        every {
            userRepository.save(any())
        } returns userEntity

        userDataSource.registerProfile("test123", profile)

        verify {
            userRepository.findByUserName("test123")
            userRepository.save(any())
        }

    }
}