package com.taurin.minpaku.unit.datasource

import com.taurin.minpaku.domain.type.Permission
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
    fun setUp() = MockKAnnotations.init(this)

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
    fun testUserDataSource() {
        val userEntity = UserEntity(
                1,
                "test123",
                "password123",
                Permission.USER
        )

        val user = userEntity.toDomain()

        every {
            userRepository.save(any())
        } returns userEntity

        userDataSource.register(user)

        verify {
            userRepository.save(any())
        }
    }
}