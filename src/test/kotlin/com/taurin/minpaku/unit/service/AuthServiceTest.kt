package com.taurin.minpaku.unit.service

import com.taurin.minpaku.infrastructure.Entity.User
import com.taurin.minpaku.enum.Permission
import com.taurin.minpaku.infrastructure.exception.DBException
import com.taurin.minpaku.infrastructure.Repository.UserRepository
import com.taurin.minpaku.service.AuthService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder

class AuthServiceTest {
    @MockK
    private lateinit var passwordEncoder: PasswordEncoder

    @MockK
    private lateinit var userRepository: UserRepository

    @InjectMockKs
    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testLoadUserByUserName() {
        val testUser = User(1, "testuser", "password", Permission.USER)
        every { userRepository.findByUserName("testuser") } returns testUser

        val actual = authService.loadUserByUsername("testuser")
        assertThat( actual.username).isEqualTo("testuser")
        assertThat( actual.password).isEqualTo("password")
    }

    @Test
    fun testRegister() {
        val testUser = User(
            null,
            "testuser",
            "password",
            Permission.USER
        )
        every { passwordEncoder.encode("password") } returns "password"
        every { userRepository.save(any()) } returns testUser
        authService.register("testuser", "password")
        verify { userRepository.save(any()) }
    }

    @Test
    fun testRegisterWithException() {
        every { passwordEncoder.encode("password") } returns "password"
        every { userRepository.save(any()) } throws RuntimeException("error!")
        try {
            authService.register("testuser", "password")
        } catch(e: DBException) {
            assertThat(e.message).isEqualTo("既にユーザ名が使用されています。")
        }
    }

    @Test
    fun testRegisterAdmin() {
        val testAdmin = User(
            null,
            "testuser",
            "password",
            Permission.ADMIN
        )
        every { userRepository.insertIgnore("testuser", "password", 1) } returns Unit
        authService.registerAdminUser(testAdmin)
        verify{ userRepository.insertIgnore("testuser", "password", 1) }
    }
}