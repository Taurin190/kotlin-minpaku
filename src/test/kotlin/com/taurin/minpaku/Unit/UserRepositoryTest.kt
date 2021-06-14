package com.taurin.minpaku.Unit

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.taurin.minpaku.Data.Repository.UserRepository
import com.taurin.minpaku.Enum.Permission
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener

@RunWith(SpringRunner::class)
@ExtendWith(SpringExtension::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["application.runner.enabled=false"]
)
@TestExecutionListeners(
    listeners = [
        DependencyInjectionTestExecutionListener::class,
        DbUnitTestExecutionListener::class
    ]
)
class UserRepositoryTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    @DatabaseSetup("/dbunit/auth1.xml")
    fun testFindByUserName() {
        var actual = userRepository.findByUserName("admin")
        assertThat(actual.userId).isEqualTo(1)
        assertThat(actual.permission).isEqualTo(Permission.ADMIN)

        actual = userRepository.findByUserName("user1")
        assertThat(actual.userId).isEqualTo(2)
        assertThat(actual.permission).isEqualTo(Permission.USER)
    }
}