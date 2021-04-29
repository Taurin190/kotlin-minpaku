package com.taurin.minpaku.Unit

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.Entity.User
import com.taurin.minpaku.Repository.UserRepository
import com.taurin.minpaku.Service.AuthService
import io.mockk.MockKAnnotations
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class AuthServiceTest {
    @MockkBean
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testLoadUserByUserName() {
        val testUser = User(1, "testuser", "password", 1)
        every { userRepository.findByUserName() } returns testUser
        val authService = AuthService(userRepository)
        val actual = authService.loadUserByUserName("testuser")
        assertTrue(actual is UserDetails)
    }
}