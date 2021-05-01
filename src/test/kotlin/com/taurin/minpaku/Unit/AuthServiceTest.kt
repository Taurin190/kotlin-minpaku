package com.taurin.minpaku.Unit

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.Entity.User
import com.taurin.minpaku.Repository.UserRepository
import com.taurin.minpaku.Service.AuthService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthServiceTest {
    @MockkBean
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var authService: AuthService

    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testLoadUserByUserName() {
        val testUser = User(1, "testuser", "password", 1)
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
            1
        )
        every { userRepository.save(any()) } returns testUser
        authService.register("testuser", "password")
        verify { userRepository.save(any()) }
    }
}