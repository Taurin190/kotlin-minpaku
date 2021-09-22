package com.taurin.minpaku.unit.service

import com.taurin.minpaku.infrastructure.Entity.Profile
import com.taurin.minpaku.infrastructure.Entity.User
import com.taurin.minpaku.domain.type.Permission
import com.taurin.minpaku.infrastructure.exception.DBException
import com.taurin.minpaku.presentation.user.ProfileNotFound
import com.taurin.minpaku.infrastructure.Repository.ProfileRepository
import com.taurin.minpaku.service.ProfileService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProfileServiceTest {
    @MockK
    private lateinit var profileRepository: ProfileRepository

    @InjectMockKs
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