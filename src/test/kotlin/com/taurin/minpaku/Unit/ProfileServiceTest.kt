package com.taurin.minpaku.Unit

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.Entity.Profile
import com.taurin.minpaku.Entity.User
import com.taurin.minpaku.Repository.ProfileRepository
import com.taurin.minpaku.Service.ProfileService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["application.runner.enabled=false"]
)
class ProfileServiceTest {
    @MockkBean
    private lateinit var profileRepository: ProfileRepository

    @Autowired
    private lateinit var profileService: ProfileService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testRegister() {
        val testUser = User(
            10,
            "testtaro",
            "password"
        )
        val testProfile = Profile(
            null,
            testUser,
            "Test Taro",
            "test@test.com",
            "test test 123",
            "123-456-789"
        )
        every { profileRepository.save(any()) } returns testProfile
        profileService.register(
            testUser,
            "Test Taro",
            "test@test.com",
            "test test 123",
            "123-456-789"
        )
        verify { profileRepository.save(any()) }
    }
}