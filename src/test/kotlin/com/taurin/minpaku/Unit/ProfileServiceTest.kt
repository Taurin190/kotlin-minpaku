package com.taurin.minpaku.Unit

import com.ninjasquad.springmockk.MockkBean
import com.taurin.minpaku.Entity.Profile
import com.taurin.minpaku.Entity.User
import com.taurin.minpaku.Enum.Permission
import com.taurin.minpaku.Exception.DBException
import com.taurin.minpaku.Exception.ProfileNotFound
import com.taurin.minpaku.Repository.ProfileRepository
import com.taurin.minpaku.Service.ProfileService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
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

        every {
            profileRepository.saveWithUserName(
            "testtaro",
            "Test Taro",
            "test@test.com",
            "test test 123",
            "123-456-789"
            )
        } returns Unit
        profileService.register(
            "testtaro",
            "Test Taro",
            "test@test.com",
            "test test 123",
            "123-456-789"
        )
        verify {
            profileRepository.saveWithUserName(
                "testtaro",
                "Test Taro",
                "test@test.com",
                "test test 123",
                "123-456-789"
            )
        }
    }

    @Test
    fun testRegisterWithException() {
        every {
            profileRepository.saveWithUserName(
                "testtaro",
                "Test Taro",
                "test@test.com",
                "test test 123",
                "123-456-789"
            )
        } throws Exception()

        try {
            profileService.register(
                "testtaro",
                "Test Taro",
                "test@test.com",
                "test test 123",
                "123-456-789"
            )
        } catch(e: DBException) {
            assertThat(e.message).isEqualTo("登録出来ませんでした。")
        }
    }

    @Test
    fun testFindByUsername() {
        val user = User(10, "testtaro", "password", Permission.USER)
        val profile = Profile(
            10,
            user,
            "Test Taro",
            "test@test.com",
            "test test 123",
            "123-456-789"
        )
        every {
            profileRepository.findByUsername("testtaro")
        } returns profile
        val actual = profileService.findByUsername("testtaro")
        assertThat(actual.name).isEqualTo("Test Taro")
    }

    @Test
    fun testFindByUsernameWithoutProfile() {
        every {
            profileRepository.findByUsername(any())
        } returns null
        try {
            profileService.findByUsername("testtaro")
        } catch(e: ProfileNotFound) {
            assertThat(e.message).isEqualTo("ユーザ情報が設定されていません。")
        }
    }

    @Test
    fun testFindByUsernameWithException() {
        every {
            profileRepository.findByUsername(any())
        } throws Exception()
        try {
            profileService.findByUsername("testtaro")
        } catch(e: DBException) {
            assertThat(e.message).isEqualTo("ユーザ情報を取得できませんでした。")
        }
    }
}